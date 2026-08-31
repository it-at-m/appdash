CREATE TABLE type_value (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    name TEXT NOT NULL,
    CONSTRAINT uq_type_value_type_name UNIQUE (type, name)
);

CREATE INDEX idx_type_value_type ON type_value(type);

CREATE TABLE app (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp_created TIMESTAMPTZ NOT NULL,
    timestamp_updated TIMESTAMPTZ NOT NULL,
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
    priority_id BIGINT REFERENCES type_value(id),
    category_id BIGINT REFERENCES type_value(id),
    mbuc_id BIGINT REFERENCES type_value(id),
    source_id BIGINT REFERENCES type_value(id),
    visibility_id BIGINT REFERENCES type_value(id)
);

CREATE INDEX idx_app_priority ON app(priority_id);
CREATE INDEX idx_app_category ON app(category_id);
CREATE INDEX idx_app_mbuc ON app(mbuc_id);
CREATE INDEX idx_app_source ON app(source_id);
CREATE INDEX idx_app_visibility ON app(visibility_id);

CREATE TABLE app_customer_info (
    app_id BIGINT NOT NULL REFERENCES app(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_id, mapping_key)
);

CREATE TABLE app_url_info (
    app_id BIGINT NOT NULL REFERENCES app(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_id, mapping_key)
);

CREATE TABLE app_img_info (
    app_id BIGINT NOT NULL REFERENCES app(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_id, mapping_key)
);

CREATE TABLE app_privacy_info (
    app_id BIGINT NOT NULL REFERENCES app(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_id, mapping_key)
);

CREATE TABLE app_process (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp_created TIMESTAMPTZ NOT NULL,
    timestamp_updated TIMESTAMPTZ NOT NULL,
    timestamp_status_updated TIMESTAMPTZ,
    timestamp_store_updated TIMESTAMPTZ,
    timestamp_focus TIMESTAMPTZ,
    timestamp_control_updated TIMESTAMPTZ,
    description TEXT,
    mdm TEXT,
    is_pilot BOOLEAN,
    is_critical BOOLEAN,
    os_id BIGINT REFERENCES type_value(id),
    trend_id BIGINT REFERENCES type_value(id),
    number_of_users_id BIGINT REFERENCES type_value(id),
    status_id BIGINT REFERENCES type_value(id),
    lane_id BIGINT REFERENCES type_value(id),
    viv_id BIGINT REFERENCES type_value(id),
    app_id BIGINT REFERENCES app(id) ON DELETE CASCADE
);

CREATE INDEX idx_app_process_app ON app_process(app_id);
CREATE INDEX idx_app_process_os ON app_process(os_id);
CREATE INDEX idx_app_process_trend ON app_process(trend_id);
CREATE INDEX idx_app_process_num_users ON app_process(number_of_users_id);
CREATE INDEX idx_app_process_status ON app_process(status_id);
CREATE INDEX idx_app_process_lane ON app_process(lane_id);
CREATE INDEX idx_app_process_viv ON app_process(viv_id);

CREATE TABLE app_process_customer_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE app_process_url_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE app_process_rsm_key_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE app_process_comment_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE app_process_license_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE app_process_cloud_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE app_process_client_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE app_process_origin_info (
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (app_process_id, mapping_key)
);

CREATE TABLE scan (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp_created TIMESTAMPTZ NOT NULL,
    timestamp_updated TIMESTAMPTZ NOT NULL,
    scan_key TEXT,
    size TEXT,
    version TEXT,
    score INTEGER,
    cnt_files INTEGER,
    file_type_id BIGINT REFERENCES type_value(id),
    provider_id BIGINT REFERENCES type_value(id),
    app_process_id BIGINT REFERENCES app_process(id) ON DELETE CASCADE
);

CREATE INDEX idx_scan_app_process ON scan(app_process_id);
CREATE INDEX idx_scan_file_type ON scan(file_type_id);
CREATE INDEX idx_scan_provider ON scan(provider_id);

CREATE TABLE app_group (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp_created TIMESTAMPTZ NOT NULL,
    timestamp_updated TIMESTAMPTZ NOT NULL,
    mail VARCHAR(254) NOT NULL,
    color VARCHAR(7) NOT NULL DEFAULT '#0088ff',
    setting_control_interval INTEGER NOT NULL DEFAULT 3,
    setting_mail_report BOOLEAN NOT NULL DEFAULT FALSE,
    setting_mail_scan_error BOOLEAN NOT NULL DEFAULT FALSE,
    setting_mail_scan_degraded BOOLEAN NOT NULL DEFAULT FALSE,
    setting_mail_scan_success BOOLEAN NOT NULL DEFAULT FALSE,
    setting_mail_app_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE app_group_assignment (
    app_group_id BIGINT NOT NULL REFERENCES app_group(id) ON DELETE CASCADE,
    app_id BIGINT NOT NULL REFERENCES app(id) ON DELETE CASCADE,
    PRIMARY KEY (app_group_id, app_id)
);

CREATE INDEX idx_app_group_assign_app ON app_group_assignment(app_id);

CREATE TABLE cosu (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp_created TIMESTAMPTZ NOT NULL,
    timestamp_updated TIMESTAMPTZ NOT NULL,
    name TEXT,
    description TEXT,
    info_url TEXT,
    mdm TEXT,
    e_key TEXT,
    epic_key TEXT,
    number_of_users_id BIGINT REFERENCES type_value(id),
    priority_id BIGINT REFERENCES type_value(id),
    os_id BIGINT REFERENCES type_value(id),
    status_id BIGINT REFERENCES type_value(id),
    lane_id BIGINT REFERENCES type_value(id)
);

CREATE INDEX idx_cosu_num_users ON cosu(number_of_users_id);
CREATE INDEX idx_cosu_priority ON cosu(priority_id);
CREATE INDEX idx_cosu_os ON cosu(os_id);
CREATE INDEX idx_cosu_status ON cosu(status_id);
CREATE INDEX idx_cosu_lane ON cosu(lane_id);

CREATE TABLE cosu_assignment (
    cosu_id BIGINT NOT NULL REFERENCES cosu(id) ON DELETE CASCADE,
    app_id BIGINT NOT NULL REFERENCES app(id) ON DELETE CASCADE,
    PRIMARY KEY (cosu_id, app_id)
);

CREATE INDEX idx_cosu_assign_app ON cosu_assignment(app_id);

CREATE TABLE cosu_comment_info (
    cosu_id BIGINT NOT NULL REFERENCES cosu(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (cosu_id, mapping_key)
);

CREATE TABLE cosu_url_info (
    cosu_id BIGINT NOT NULL REFERENCES cosu(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (cosu_id, mapping_key)
);

CREATE TABLE cosu_client_info (
    cosu_id BIGINT NOT NULL REFERENCES cosu(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (cosu_id, mapping_key)
);

CREATE TABLE cosu_origin_info (
    cosu_id BIGINT NOT NULL REFERENCES cosu(id) ON DELETE CASCADE,
    mapping_key VARCHAR(255) NOT NULL,
    mapping_value TEXT,
    PRIMARY KEY (cosu_id, mapping_key)
);

CREATE TABLE timeline_event (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type INTEGER,
    timestamp_start TIMESTAMPTZ NOT NULL,
    timestamp_end TIMESTAMPTZ,
    status_id BIGINT NOT NULL REFERENCES type_value(id),
    app_process_id BIGINT NOT NULL REFERENCES app_process(id) ON DELETE CASCADE
);

CREATE INDEX idx_timeline_event_app_process ON timeline_event(app_process_id);
CREATE INDEX idx_timeline_event_status ON timeline_event(status_id);

CREATE TABLE ai_scan_report (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp_created TIMESTAMPTZ NOT NULL,
    timestamp_updated TIMESTAMPTZ NOT NULL,
    libraries TEXT,
    permissions TEXT,
    domains TEXT,
    general TEXT,
    summary TEXT,
    scan_id BIGINT NOT NULL UNIQUE REFERENCES scan(id) ON DELETE CASCADE
);

CREATE TABLE ai_scan_comparison (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timestamp_created TIMESTAMPTZ NOT NULL,
    timestamp_updated TIMESTAMPTZ NOT NULL,
    libraries TEXT,
    permissions TEXT,
    domains TEXT,
    general TEXT,
    summary TEXT,
    scan_one_id BIGINT NOT NULL REFERENCES scan(id) ON DELETE CASCADE,
    scan_two_id BIGINT NOT NULL REFERENCES scan(id) ON DELETE CASCADE
);

CREATE INDEX idx_ai_scan_comp_scan_one ON ai_scan_comparison(scan_one_id);
CREATE INDEX idx_ai_scan_comp_scan_two ON ai_scan_comparison(scan_two_id);