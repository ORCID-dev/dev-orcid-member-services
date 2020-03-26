# User settings service

The `user-settings-service` is the user management microservice. It suports creating, reading and updating users, as well as managing their roles and permissions.

## Prerequisites

Configure the orcid-mermber-services JHipster UAA services, as explained [here](README.md).

## Start user-settings-service

1. Open a new terminal 
2. cd orcid-member-services/user-settings-service/
3. Run `bash mvnw`
4. Wait for it to start

## User API

### Generate access token

Generate an access token using the default admin user credentials `admin / admin`:

````
curl -X POST --data "username=admin&password=admin&grant_type=password&scope=openid" http://web_app:changeit@localhost:9999/oauth/token
````

Response:

```json
{
  "access_token" : "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.h61Api8W21fjqWKB1YGd-jmrw76Z81qauD9W6SMYsXj8LP9_vXvXh2deX6Lyx_NUPdzNJwnBQZs7HKS5DgcoiCA5Ji_kUXC8TfLnD9SmcCcHbr-usNMg9b5N_7liRfz6h8Yh5fcrnDErCVezZwN3_hLSce9PeT0ccX6aY-8VnlB7pZcHyNPN0np1TRUwRkNxOfbwOLOiMBTXVCUlDXos2F9qNruCkar0QUZ3URmxtm63cG1aHLzekxf2Fuvayfkr0upEoucXfD9A-hzB1YPvIvMe7eGHvFtDFH84ROzz0gZyQanoBafCpVmQv8xgBd2jcIUNnZBoN9JteFMhsNDscA",
  "token_type" : "bearer",
  "refresh_token" : "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJhdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiJiNGM2YjA4MC1mZDgzLTQ1OTYtOTM1Ny1lMWQ1OGFhNGY1NTQiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.ImuR79eDLvrL9ZtY58kH-BhsgxRXuELy-AjZIGRGOe9o3QdjFFMkZK9ajaVMCCarnJ3v_JeUEtqSNR02wMTbypd5Pa_cFd0dQYg6pCkQ6L7pJ4ev-ihFmlst-LoJDcg87ZthTspHe9zX5wtYmhASbIOh89fBJEbXXqeefUp9BoQ4ZeSG7RKUF0VZwLyJOzrGuHHWDUUwNQNAc8uS2BNwFpUhndxLRNKrKXvK1swpqJyZVp7Ao_RNFcGZyIkIHRLnXdGjrnFhna7Hk1-3lIZ01V0EcmSqWJWHAM1Tr01iXRFZCT_r7MJ0O0QCijnB8-8Va8WeS5YJ6Vi2SC1krO2kkQ",
  "expires_in" : 604799,
  "scope" : "openid",
  "iat" : 1584026648,
  "jti" : "25875c34-6438-4e6b-9bf5-4ab284638269"
}
```

### Create new member
Each user is associated with a member, so before creating users, you need to create 1 or more members.
```
curl -i -H "Accept: application/json" -H "Content-Type:application/json" -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.h61Api8W21fjqWKB1YGd-jmrw76Z81qauD9W6SMYsXj8LP9_vXvXh2deX6Lyx_NUPdzNJwnBQZs7HKS5DgcoiCA5Ji_kUXC8TfLnD9SmcCcHbr-usNMg9b5N_7liRfz6h8Yh5fcrnDErCVezZwN3_hLSce9PeT0ccX6aY-8VnlB7pZcHyNPN0np1TRUwRkNxOfbwOLOiMBTXVCUlDXos2F9qNruCkar0QUZ3URmxtm63cG1aHLzekxf2Fuvayfkr0upEoucXfD9A-hzB1YPvIvMe7eGHvFtDFH84ROzz0gZyQanoBafCpVmQv8xgBd2jcIUNnZBoN9JteFMhsNDscA" -X POST --data '{"salesforceId":"SF1","parentSalesforceId":"PSF2","clientName":"Client 1","isConsortiumLead": "false","assertionServiceEnabled":"true"}'  http://localhost:8081/settings/api/member-settings

```

### Create new user

```
curl -i -H "Accept: application/json" -H "Content-Type:application/json" -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.h61Api8W21fjqWKB1YGd-jmrw76Z81qauD9W6SMYsXj8LP9_vXvXh2deX6Lyx_NUPdzNJwnBQZs7HKS5DgcoiCA5Ji_kUXC8TfLnD9SmcCcHbr-usNMg9b5N_7liRfz6h8Yh5fcrnDErCVezZwN3_hLSce9PeT0ccX6aY-8VnlB7pZcHyNPN0np1TRUwRkNxOfbwOLOiMBTXVCUlDXos2F9qNruCkar0QUZ3URmxtm63cG1aHLzekxf2Fuvayfkr0upEoucXfD9A-hzB1YPvIvMe7eGHvFtDFH84ROzz0gZyQanoBafCpVmQv8xgBd2jcIUNnZBoN9JteFMhsNDscA" -X POST --data '{"login":"test@mailinator.com", "firstName":"Firstname", "lastName":"Lastname", "password":"password123","authorities":["ROLE_USER"],"salesforceId":"SF1"}'  http://localhost:8081/settings/api/user

```

From it, notice the user parameters: 

- login: The user email
- password: The user password
- authorities: The authorities the user should have; all users should have `ROLE_USER`, users who should have access to add/edit affiliations should also have `ASSERTION_SERVICE_ENABLED`
- salesforceId: The Salesforce ID of the member organization that the user is associated with. Must be an existing member (see create new member above).

Response:

```json
{
  "id" : "5e6a5e3c6798abba0f754535",
  "login" : "test@mailinator.com",
  "loginError" : null,
  "password" : null,
  "firstName" : "Firstname",
  "firstNameError" : null,
  "lastName" : "Lastname",
  "lastNameError" : null,
  "mainContact" : null,
  "assertionServicesEnabled" : null,
  "salesforceId" : "SF1",
  "salesforceIdError" : null,
  "createdBy" : "admin",
  "createdDate" : "2020-03-12T16:07:24.380Z",
  "lastModifiedBy" : "internal",
  "lastModifiedDate" : "2020-03-12T16:07:24.380Z"
}
```

Save the `id` and `login` parameters somewhere because you will need it later on.

After that, please go to the (gateway app)[http://localhost:8080/], logout if you are not, and, now login using the new credentials you just created.

**DB entries for new users:**
- In the `Oauth2Service` collection, in the `jhi_user` table, you will see a new user, which is the one used by the UAA to allow you to login.
- In the `UserSettingsService` collection, in the `member_services_user` table, there will be a new user as well, and, on it, the `user_id` field must match the id of the new user in the `Oauth2Service`.`jhi_user` table

### Retrieve user

Now we want to retrive the information of a specific user, so, from the prevous list, get the `login` of the user you want to fetch, and replace the `{login}` in the following CURL command with that value:

```
curl -i -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.h61Api8W21fjqWKB1YGd-jmrw76Z81qauD9W6SMYsXj8LP9_vXvXh2deX6Lyx_NUPdzNJwnBQZs7HKS5DgcoiCA5Ji_kUXC8TfLnD9SmcCcHbr-usNMg9b5N_7liRfz6h8Yh5fcrnDErCVezZwN3_hLSce9PeT0ccX6aY-8VnlB7pZcHyNPN0np1TRUwRkNxOfbwOLOiMBTXVCUlDXos2F9qNruCkar0QUZ3URmxtm63cG1aHLzekxf2Fuvayfkr0upEoucXfD9A-hzB1YPvIvMe7eGHvFtDFH84ROzz0gZyQanoBafCpVmQv8xgBd2jcIUNnZBoN9JteFMhsNDscA" -X GET  http://localhost:8081/settings/api/user/{login}
```

That will return the information of that specific user.

### Update user

Now, we want to update the name of the new user, for that, we will need to reuse the json data of the user, but changing the first name and last name for a new value, as follows:

```
curl -i -H "Accept: application/json" -H "Content-Type:application/json" -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.h61Api8W21fjqWKB1YGd-jmrw76Z81qauD9W6SMYsXj8LP9_vXvXh2deX6Lyx_NUPdzNJwnBQZs7HKS5DgcoiCA5Ji_kUXC8TfLnD9SmcCcHbr-usNMg9b5N_7liRfz6h8Yh5fcrnDErCVezZwN3_hLSce9PeT0ccX6aY-8VnlB7pZcHyNPN0np1TRUwRkNxOfbwOLOiMBTXVCUlDXos2F9qNruCkar0QUZ3URmxtm63cG1aHLzekxf2Fuvayfkr0upEoucXfD9A-hzB1YPvIvMe7eGHvFtDFH84ROzz0gZyQanoBafCpVmQv8xgBd2jcIUNnZBoN9JteFMhsNDscA" -X PUT --data '{"id":"5e6a5e3c6798abba0f754535", "login":"test@mailinator.com", "firstName":"Awesome", "lastName":"Lastname", "password":"password123","authorities":["ROLE_USER"],"salesforceId":"SF1"}'  http://localhost:8081/settings/api/user
```
Notice we now include the `"id"` field, which represent the users id in the database. 
If that works fine, you will get a `200 OK` from the server, along with the updated user info in JSON format

### Delete privilege (authority) from user
Currently, it's not possible to delete users. You can, however, delete authorities from users, which restricts them from actions such as logging in or accessing assertions;

Replace `{id}` and `{authority}` n the following CURL command. Available authorities are `ROLE_USER` and `ASSERTION_SERVICE_ENABLED`

```
curl -i -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.h61Api8W21fjqWKB1YGd-jmrw76Z81qauD9W6SMYsXj8LP9_vXvXh2deX6Lyx_NUPdzNJwnBQZs7HKS5DgcoiCA5Ji_kUXC8TfLnD9SmcCcHbr-usNMg9b5N_7liRfz6h8Yh5fcrnDErCVezZwN3_hLSce9PeT0ccX6aY-8VnlB7pZcHyNPN0np1TRUwRkNxOfbwOLOiMBTXVCUlDXos2F9qNruCkar0QUZ3URmxtm63cG1aHLzekxf2Fuvayfkr0upEoucXfD9A-hzB1YPvIvMe7eGHvFtDFH84ROzz0gZyQanoBafCpVmQv8xgBd2jcIUNnZBoN9JteFMhsNDscA" -X DELETE  http://localhost:8081/settings/api/user/{id}/{authority}
```

If successful, the server will respond with a `202 Accepted`

### Create multiple members from CSV

```csv
salesforceId,parentSalesforceId,clientName,clientId,isConsortiumLead, assertionServiceEnabled
SF4,PSF1,Client 4,APP-XXXXXXXXXXXXXXX4,false,true
SF5,PSF2,Client 5,APP-XXXXXXXXXXXXXXX5,false,true
```

Notice that the first line in the file must be the header, which defines the fields we should include as part of each user:

name | required
-----| ----------
salesforceId | Yes
parentSalesforceId | No
clientName | No 
clientId | No
isConsortiumLead | No
assertionServiceEnabled | No

So, save the above csv as file `members.csv` and import it as follow:

```
curl -i -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1NzQ3ODIwNDUsImlhdCI6MTU3NDE3NzI0NSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyZTBkODYxZS1kNDg1LTQ3ZGItODJhZS02MGY0NGU4YTNlMWEiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.J1Qfl75v1JohAfi0Mbv_h0-g8EK7i-yhfJZ1is-5-nwPrOMaQk7NwSGp9_GFJktYCA7SCvGjODkgyvjKiBiZ1M2_rJNa5BnL_foL6rr98zTtwv0NmsGUXIyehrbc03aTdLYjQCh9svNspfrPuGKYm_IkfoWkMb6BcZ5MgvI5DaLh2aIciLtCGUY-eG3s4CMfrquEcgxn4a_F9eIX9TGA9ixRZvJj9EixXv7ZorOlfeiY749Ra8v1a-aX34fWJX2Uvyq5sODFx6IE0f81iTdODwAe2FS0xN02YSqoyOFIKU1j0DP1wuTEpMzlqeRwnvlVy0-3q9VMgCdUKMlL1ze5aQ" -F file=@members.csv -X POST  http://localhost:8081/settings/api/member-settings/upload
```

### Create multiple users from CSV

Now we want to test that you can upload multiple users at once, this is done through the CSV user inport endpoint, but, before going into the technical details, lets see how the CSV should look like: 

```csv
salesforceId,email,firstName,lastName,grant
SF1,test1@mailinator.com,FirstName5,LastName5,"[ROLE_USER,ASSERTION_SERVICE_ENABLED]"
SF2,test2@mailinator.com,FirstName6,LastName6,"[ROLE_USER,ASSERTION_SERVICE_ENABLED]"
```

Notice that the first line in the file must be the header, which defines the fields we should include as part of each user:

name | required
-----| ----------
salesforceId | Yes (must be an existing member)
email | Yes
firstName | No 
lastName | No
grant | No

So, save the above csv as file `users.csv` and import it as follow:

```
curl -i -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1NzQ3ODIwNDUsImlhdCI6MTU3NDE3NzI0NSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyZTBkODYxZS1kNDg1LTQ3ZGItODJhZS02MGY0NGU4YTNlMWEiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.J1Qfl75v1JohAfi0Mbv_h0-g8EK7i-yhfJZ1is-5-nwPrOMaQk7NwSGp9_GFJktYCA7SCvGjODkgyvjKiBiZ1M2_rJNa5BnL_foL6rr98zTtwv0NmsGUXIyehrbc03aTdLYjQCh9svNspfrPuGKYm_IkfoWkMb6BcZ5MgvI5DaLh2aIciLtCGUY-eG3s4CMfrquEcgxn4a_F9eIX9TGA9ixRZvJj9EixXv7ZorOlfeiY749Ra8v1a-aX34fWJX2Uvyq5sODFx6IE0f81iTdODwAe2FS0xN02YSqoyOFIKU1j0DP1wuTEpMzlqeRwnvlVy0-3q9VMgCdUKMlL1ze5aQ" -F file=@users.csv -X POST  http://localhost:8081/settings/api/user/upload
```

The server will process the file and return a json result like the following one: 

```json
{
  "1" : "5dd598c72c833775062406a2",
  "2" : "5dd598c72c833775062406a3"
}
```

Which indicates that the user in line `1` was created with id `5dd598c72c833775062406a2` and user in line `2` was created with id `5dd598c72c833775062406a3`

If any of your lines have an error, the server will put the error in the given line index and the process the rest of the lines, for example:

```json
{
  "1" : "5dd598c72c833775062406a2",
  "2" : "Login should not be empty, Email should not be empty"
}
```

### Retrieve all users

Now we want to fetch all existing users, to do that, execute the following command:

```
curl -i -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1ODQ2MzE0NDgsImlhdCI6MTU4NDAyNjY0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyNTg3NWMzNC02NDM4LTRlNmItOWJmNS00YWIyODQ2MzgyNjkiLCJjbGllbnRfaWQiOiJ3ZWJfYXBwIn0.h61Api8W21fjqWKB1YGd-jmrw76Z81qauD9W6SMYsXj8LP9_vXvXh2deX6Lyx_NUPdzNJwnBQZs7HKS5DgcoiCA5Ji_kUXC8TfLnD9SmcCcHbr-usNMg9b5N_7liRfz6h8Yh5fcrnDErCVezZwN3_hLSce9PeT0ccX6aY-8VnlB7pZcHyNPN0np1TRUwRkNxOfbwOLOiMBTXVCUlDXos2F9qNruCkar0QUZ3URmxtm63cG1aHLzekxf2Fuvayfkr0upEoucXfD9A-hzB1YPvIvMe7eGHvFtDFH84ROzz0gZyQanoBafCpVmQv8xgBd2jcIUNnZBoN9JteFMhsNDscA" -X GET  http://localhost:8081/settings/api/users
```

That will return a list of all existing users


