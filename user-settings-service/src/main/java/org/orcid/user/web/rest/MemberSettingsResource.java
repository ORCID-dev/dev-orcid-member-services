package org.orcid.user.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.codehaus.jettison.json.JSONException;
import org.orcid.user.domain.MemberSettings;
import org.orcid.user.domain.UserSettings;
import org.orcid.user.domain.validation.MemberSettingsValidator;
import org.orcid.user.repository.MemberSettingsRepository;
import org.orcid.user.repository.UserSettingsRepository;
import org.orcid.user.security.UaaUserUtils;
import org.orcid.user.upload.MembersUpload;
import org.orcid.user.upload.impl.MembersCsvReader;
import org.orcid.user.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.orcid.user.domain.MemberSettings}.
 */
@RestController
@RequestMapping("/settings/api")
public class MemberSettingsResource {

	private final Logger log = LoggerFactory.getLogger(MemberSettingsResource.class);

	private static final String ENTITY_NAME = "userSettingsServiceMemberSettings";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private UaaUserUtils uaaUserUtils;

	@Autowired
	private UserSettingsRepository userSettingsRepository;

	@Autowired
	private MemberSettingsRepository memberSettingsRepository;

	@Autowired
	private MembersCsvReader membersCsvReader;

	/**
	 * {@code POST  /member-settings} : Create a new memberSettings.
	 *
	 * @param memberSettings the memberSettings to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new memberSettings, or with status {@code 400 (Bad Request)}
	 *         if the memberSettings has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws JSONException
	 */
	@PostMapping("/member-settings")
	public ResponseEntity<MemberSettings> createMemberSettings(@Valid @RequestBody MemberSettings memberSettings)
			throws URISyntaxException, JSONException {
		log.debug("REST request to save MemberSettings : {}", memberSettings);
		if (memberSettings.getId() != null) {
			throw new BadRequestAlertException("A new memberSettings cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		Optional<MemberSettings> optional = memberSettingsRepository
				.findBySalesforceId(memberSettings.getSalesforceId());
		// If user doesn't exists, create it
		if (optional.isPresent()) {
			throw new BadRequestAlertException("A member settings with that salesforce id already exists", ENTITY_NAME,
					"idexists");
		}
		if (!MemberSettingsValidator.validate(memberSettings)) {
			ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(applicationName, true, ENTITY_NAME,
					"memberSettings.create.error", memberSettings.getError()));
		}

		Instant now = Instant.now();
		memberSettings.setCreatedBy(uaaUserUtils.getAuthenticatedUaaUserId());
		memberSettings.setCreatedDate(now);
		memberSettings.setLastModifiedBy(uaaUserUtils.getAuthenticatedUaaUserId());
		memberSettings.setLastModifiedDate(now);

		MemberSettings result = memberSettingsRepository.save(memberSettings);
		return ResponseEntity
				.created(new URI("/api/member-settings/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code POST  /member-settings/upload} : Create a list of member settings.
	 *
	 * @param file: file containing the member-settings to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         a map indicating if each user was created or not, or with status
	 *         {@code 400 (Bad Request)} if the file cannot be parsed.
	 * @throws Throwable
	 */
	@PostMapping("/member-settings/upload")
	@PreAuthorize("hasRole(\"ROLE_ADMIN\")")
	public ResponseEntity<String> uploadMemberSettings(@RequestParam("file") MultipartFile file) throws Throwable {
		log.debug("Uploading member settings CSV");
		MembersUpload upload = membersCsvReader.readMembersUpload(file.getInputStream());
		for (MemberSettings memberSettings : upload.getMembers()) {
			Instant now = Instant.now();
			Optional<MemberSettings> optional = memberSettingsRepository
					.findBySalesforceId(memberSettings.getSalesforceId());

			if (!optional.isPresent()) {
				memberSettings.setCreatedBy(uaaUserUtils.getAuthenticatedUaaUserId());
				memberSettings.setCreatedDate(now);
				memberSettings.setLastModifiedBy(uaaUserUtils.getAuthenticatedUaaUserId());
				memberSettings.setLastModifiedDate(now);
				memberSettingsRepository.save(memberSettings);
			} else {
				// If it exists, update it
				MemberSettings existingMemberSettings = optional.get();
				existingMemberSettings.setAssertionServiceEnabled(memberSettings.getAssertionServiceEnabled());
				existingMemberSettings.setClientId(memberSettings.getClientId());
				existingMemberSettings.setIsConsortiumLead(memberSettings.getIsConsortiumLead());
				existingMemberSettings.setParentSalesforceId(memberSettings.getParentSalesforceId());
				memberSettings.setLastModifiedBy(uaaUserUtils.getAuthenticatedUaaUserId());
				memberSettings.setLastModifiedDate(now);
				memberSettingsRepository.save(existingMemberSettings);
			}
		}
		return ResponseEntity.ok().body(upload.getErrors().toString());
	}

	/**
	 * {@code PUT  /member-settings} : Updates an existing memberSettings.
	 *
	 * @param memberSettings the memberSettings to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated memberSettings, or with status {@code 400 (Bad Request)}
	 *         if the memberSettings is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the memberSettings couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws JSONException
	 */
	@PutMapping("/member-settings")
	public ResponseEntity<MemberSettings> updateMemberSettings(@Valid @RequestBody MemberSettings memberSettings)
			throws URISyntaxException, JSONException {
		log.debug("REST request to update MemberSettings : {}", memberSettings);
		if (memberSettings.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Optional<MemberSettings> mso = memberSettingsRepository.findById(memberSettings.getId());
		if (!mso.isPresent()) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idunavailable");
		}
		if (!MemberSettingsValidator.validate(memberSettings)) {
			ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(applicationName, true, ENTITY_NAME,
					"memberSettings.create.error", memberSettings.getError()));
		}

		Instant now = Instant.now();
		memberSettings.setLastModifiedBy(uaaUserUtils.getAuthenticatedUaaUserId());
		memberSettings.setLastModifiedDate(now);
		MemberSettings result = memberSettingsRepository.save(memberSettings);

		// Check if salesforceId changed
		MemberSettings existingMemberSettings = mso.get();

		if (!existingMemberSettings.getSalesforceId().equals(memberSettings.getSalesforceId())) {
			// If salesforceId changed, update each of the existing users with
			// the new salesforceId
			List<UserSettings> usList = userSettingsRepository
					.findBySalesforceId(existingMemberSettings.getSalesforceId());
			for (UserSettings us : usList) {
				us.setSalesforceId(memberSettings.getSalesforceId());
				us.setLastModifiedBy(uaaUserUtils.getAuthenticatedUaaUserId());
				us.setLastModifiedDate(now);
				userSettingsRepository.save(us);
			}
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				memberSettings.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /member-settings} : get all the memberSettings.
	 *
	 *
	 * @param pageable the pagination information.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of memberSettings in body.
	 */
	@GetMapping("/member-settings")
	public ResponseEntity<List<MemberSettings>> getAllMemberSettings(Pageable pageable) {
		log.debug("REST request to get a page of MemberSettings");
		Page<MemberSettings> page = memberSettingsRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /member-settings/:id} : get the "id" memberSettings.
	 *
	 * @param id - the id or salesforce id of the memberSettings to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the memberSettings, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/member-settings/{id}")
	public ResponseEntity<MemberSettings> getMemberSettings(@PathVariable String id) {
		log.debug("REST request to get MemberSettings : {}", id);
		Optional<MemberSettings> memberSettings = memberSettingsRepository.findById(id);
		if (!memberSettings.isPresent()) {
			log.debug("Member settings now found for id {}, searching against salesforceId", id);
			memberSettings = memberSettingsRepository.findBySalesforceId(id);
		}
		return ResponseUtil.wrapOrNotFound(memberSettings);
	}

	/**	
	 * {@code DELETE  /member-settings/:id} : delete the "id" memberSettings.
	 *
	 * @param id the id of the memberSettings to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/member-settings/{id}")
	public ResponseEntity<Void> deleteMemberSettings(@PathVariable String id) {
		log.debug("REST request to delete MemberSettings : {}", id);

		// Can't delete a memberSettings object if there is at least one
		// userSettings linked to it
		Optional<MemberSettings> mso = memberSettingsRepository.findById(id);
		if (!mso.isPresent()) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idunavailable");
		}

		// If there is at least one userSettings assigned to this
		// memberSettings, throw an exception
		userSettingsRepository.findBySalesforceId(id).stream().map(userSettings -> {
			throw new BadRequestAlertException(
					"Unable to delete MemberSettings, user '" + userSettings.getJhiUserId() + "' still use it",
					ENTITY_NAME, "idused");
		});

		memberSettingsRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
	}

}
