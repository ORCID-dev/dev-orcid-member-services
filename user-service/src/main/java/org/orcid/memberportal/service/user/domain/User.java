package org.orcid.memberportal.service.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.orcid.memberportal.service.user.config.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A user.
 */
@org.springframework.data.mongodb.core.mapping.Document(collection = "jhi_user")
public class User extends AbstractAuditingEntity implements Serializable {

      private static final long serialVersionUID = 1L;

      @Id
      private String id;

      @JsonIgnore
      @NotNull
      @Size(min = 10, max = 60)
      private String password;

      @Size(max = 50)
      @Field("first_name")
      private String firstName;

      @Size(max = 50)
      @Field("last_name")
      private String lastName;

      @Email
      @Size(min = 5, max = 254)
      @Indexed
      private String email;

      private boolean activated = false;

      @Size(min = 2, max = 10)
      @Field("lang_key")
      private String langKey = Constants.DEFAULT_LANGUAGE;

      @Size(max = 256)
      @Field("image_url")
      private String imageUrl;

      @Size(max = 20)
      @Field("activation_key")
      @JsonIgnore
      private String activationKey;

      @Field("activation_date")
      private Instant activationDate = null;

      @Field("activation_reminders")
      private List<ActivationReminder> activationReminders;

      @Size(max = 20)
      @Field("reset_key")
      @JsonIgnore
      private String resetKey;

      @Field("reset_date")
      private Instant resetDate = null;

      @Field("salesforce_id")
      private String salesforceId;

      @Field("member_name")
      private String memberName;

      @Field("main_contact")
      private Boolean mainContact;

      @Field("deleted")
      private Boolean deleted = false;

      @Field("login_as")
      private String loginAs;

      @Field("mfa_enabled")
      private Boolean mfaEnabled;

      @Field("mfa_encrypted_secret")
      private String mfaEncryptedSecret;

      @Field("mfa_backup_codes")
      private List<String> mfaBackupCodes;

      @Field("admin")
      private Boolean admin = false;

      public String getLoginAs() {
            return loginAs;
      }

      public void setLoginAs(String loginAs) {
            this.loginAs = loginAs;
      }

      public String getId() {
            return id;
      }

      public void setId(String id) {
            this.id = id;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public String getFirstName() {
            return firstName;
      }

      public void setFirstName(String firstName) {
            this.firstName = firstName;
      }

      public String getLastName() {
            return lastName;
      }

      public void setLastName(String lastName) {
            this.lastName = lastName;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = StringUtils.lowerCase(email, Locale.ENGLISH);
      }

      public String getImageUrl() {
            return imageUrl;
      }

      public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
      }

      public boolean getActivated() {
            return activated;
      }

      public void setActivated(boolean activated) {
            this.activated = activated;
      }

      public String getActivationKey() {
            return activationKey;
      }

      public void setActivationKey(String activationKey) {
            this.activationKey = activationKey;
      }

      public Instant getActivationDate() {
            return activationDate;
      }

      public void setActivationDate(Instant activationDate) {
            this.activationDate = activationDate;
      }

      public String getResetKey() {
            return resetKey;
      }

      public void setResetKey(String resetKey) {
            this.resetKey = resetKey;
      }

      public Instant getResetDate() {
            return resetDate;
      }

      public void setResetDate(Instant resetDate) {
            this.resetDate = resetDate;
      }

      public String getLangKey() {
            return langKey;
      }

      public void setLangKey(String langKey) {
            this.langKey = langKey;
      }

      public String getMemberName() {
            return memberName;
      }

      public void setMemberName(String memberName) {
            this.memberName = memberName;
      }

      public String getSalesforceId() {
            return salesforceId;
      }

      public void setSalesforceId(String salesforceId) {
            this.salesforceId = salesforceId;
      }

      public Boolean getMainContact() {
            return mainContact;
      }

      public void setMainContact(Boolean mainContact) {
            this.mainContact = mainContact;
      }

      public Boolean getDeleted() {
            return deleted;
      }

      public void setDeleted(Boolean deleted) {
            this.deleted = deleted;
      }

      public List<ActivationReminder> getActivationReminders() {
            return activationReminders;
      }

      public void setActivationReminders(
            List<ActivationReminder> activationReminders
      ) {
            this.activationReminders = activationReminders;
      }

      public Boolean getMfaEnabled() {
            return mfaEnabled;
      }

      public void setMfaEnabled(Boolean mfaEnabled) {
            this.mfaEnabled = mfaEnabled;
      }

      public String getMfaEncryptedSecret() {
            return mfaEncryptedSecret;
      }

      public void setMfaEncryptedSecret(String mfaEncryptedSecret) {
            this.mfaEncryptedSecret = mfaEncryptedSecret;
      }

      public List<String> getMfaBackupCodes() {
            return mfaBackupCodes;
      }

      public void setMfaBackupCodes(List<String> mfaBackupCodes) {
            this.mfaBackupCodes = mfaBackupCodes;
      }

      public Boolean getAdmin() {
            return admin;
      }

      public void setAdmin(Boolean admin) {
            this.admin = admin;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) {
                  return true;
            }
            if (!(o instanceof User)) {
                  return false;
            }
            return id != null && id.equals(((User) o).id);
      }

      @Override
      public int hashCode() {
            return (
                  32 +
                  (id != null ? id.hashCode() : 1) +
                  (email != null ? email.hashCode() : 2)
            );
      }

      @Override
      public String toString() {
            return (
                  "User{firstName='" +
                  firstName +
                  '\'' +
                  ", lastName='" +
                  lastName +
                  '\'' +
                  ", email='" +
                  email +
                  '\'' +
                  ", imageUrl='" +
                  imageUrl +
                  '\'' +
                  ", activated='" +
                  activated +
                  '\'' +
                  ", langKey='" +
                  langKey +
                  '\'' +
                  ", activationKey='" +
                  activationKey +
                  '\'' +
                  "}"
            );
      }
}
