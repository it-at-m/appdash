CREATE TABLE revision_user (
    rev INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    revtstmp BIGINT,
    username VARCHAR(255)
);


CREATE TABLE app_aud (
    id BIGINT NOT NULL,
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    name TEXT,
    creator TEXT,
    img TEXT,
    description TEXT,
    bundle_id TEXT,
    appstore_id TEXT,
    e_key TEXT,
    epic_key TEXT,
    privacy_policy TEXT,
    website TEXT,
    is_internal BOOLEAN,
    priority_id BIGINT,
    category_id BIGINT,
    mbuc_id BIGINT,
    source_id BIGINT,
    visibility_id BIGINT,
    PRIMARY KEY (id, rev)
);

CREATE TABLE app_customer_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    app_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, app_id, mapping_key)
);

CREATE TABLE app_url_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    app_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, app_id, mapping_key)
);

CREATE TABLE app_img_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    app_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, app_id, mapping_key)
);

CREATE TABLE app_privacy_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    app_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, app_id, mapping_key)
);

CREATE TABLE process_aud (
    id BIGINT NOT NULL,
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    timestamp_status_updated TIMESTAMPTZ,
    timestamp_store_updated TIMESTAMPTZ,
    timestamp_focus TIMESTAMPTZ,
    timestamp_control_updated TIMESTAMPTZ,
    description TEXT,
    mdm TEXT,
    is_pilot BOOLEAN,
    is_critical BOOLEAN,
    os_id BIGINT,
    trend_id BIGINT,
    number_of_users_id BIGINT,
    status_id BIGINT,
    lane_id BIGINT,
    viv_id BIGINT,
    app_id BIGINT,
    PRIMARY KEY (id, rev)
);

CREATE TABLE process_customer_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE process_url_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE process_rsm_key_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE process_comment_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE process_license_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE process_cloud_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE process_client_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE process_origin_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    process_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, process_id, mapping_key)
);

CREATE TABLE cosu_aud (
    id BIGINT NOT NULL,
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    name TEXT,
    description TEXT,
    info_url TEXT,
    mdm TEXT,
    e_key TEXT,
    epic_key TEXT,
    number_of_users_id BIGINT,
    priority_id BIGINT,
    os_id BIGINT,
    status_id BIGINT,
    lane_id BIGINT,
    PRIMARY KEY (id, rev)
);

CREATE TABLE cosu_comment_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    cosu_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, cosu_id, mapping_key)
);

CREATE TABLE cosu_url_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    cosu_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, cosu_id, mapping_key)
);

CREATE TABLE cosu_client_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    cosu_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, cosu_id, mapping_key)
);

CREATE TABLE cosu_origin_info_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    setordinal INTEGER NOT NULL,
    cosu_id BIGINT NOT NULL,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (rev, cosu_id, mapping_key)
);

CREATE TABLE cosu_assignment_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    cosu_id BIGINT NOT NULL,
    app_id BIGINT NOT NULL,
    PRIMARY KEY (rev, cosu_id, app_id)
);

CREATE TABLE app_group_aud (
    id BIGINT NOT NULL,
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    mail VARCHAR(254),
    color VARCHAR(7),
    setting_control_interval INTEGER,
    setting_mail_report BOOLEAN,
    setting_mail_scan_error BOOLEAN,
    setting_mail_scan_degraded BOOLEAN,
    setting_mail_scan_success BOOLEAN,
    setting_mail_app_removed BOOLEAN,
    PRIMARY KEY (id, rev)
);

CREATE TABLE app_group_assignment_aud (
    rev INTEGER NOT NULL REFERENCES revision_user(rev),
    revtype SMALLINT NOT NULL,
    app_group_id BIGINT NOT NULL,
    app_id BIGINT NOT NULL,
    PRIMARY KEY (rev, app_group_id, app_id)
);