CREATE TABLE invoices (
                          id BIGSERIAL PRIMARY KEY,
                          order_id BIGINT NOT NULL UNIQUE REFERENCES orders(id),
                          invoice_number VARCHAR(20) NOT NULL,
                          series VARCHAR(10) NOT NULL DEFAULT '1',
                          access_key VARCHAR(44) UNIQUE, -- Chave de acesso de 44 dígitos da NF-e
                          status VARCHAR(30) NOT NULL DEFAULT 'AUTHORIZED', -- AUTHORIZED, CANCELLED, PENDING
                          xml_content TEXT, -- Simulação do XML gerado para a SEFAZ
                          issued_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_invoices_access_key ON invoices(access_key);

