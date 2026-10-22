CREATE TABLE shipments (
                           id BIGSERIAL PRIMARY KEY,
                           order_id BIGINT NOT NULL REFERENCES orders(id),
                           tracking_code VARCHAR(50) UNIQUE NOT NULL,
                           carrier_name VARCHAR(100), -- Nome da transportadora ou "Frota Própria"
                           status VARCHAR(30) NOT NULL DEFAULT 'PENDING_SEPARATION', -- PENDING_SEPARATION, SEPARATED, DISPATCHED, DELIVERED, RETURNED
                           dispatched_at TIMESTAMP WITHOUT TIME ZONE,
                           delivered_at TIMESTAMP WITHOUT TIME ZONE,
                           created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_shipments_tracking ON shipments(tracking_code);

