CREATE TABLE CENTROS_FORENSES (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    pais VARCHAR(100) NOT NULL,
    nivel_seguridad INTEGER,
    autor_examen VARCHAR(150) NOT NULL
);


CREATE TABLE MUESTRAS_FORENSES (
    id SERIAL PRIMARY KEY,
    codigo_caso INTEGER NOT NULL,
    tipo_muestra VARCHAR(100) NOT NULL,
    fecha_recogida DATE,
    estado_custodia VARCHAR(100) NOT NULL,
    centro_id INT UNIQUE NOT NULL,
    autor_examen VARCHAR(150) NOT NULL,

    CONSTRAINT fk_muestras_centros
        FOREIGN KEY (centro_id)
        REFERENCES CENTROS_FORENSES(id)
        ON DELETE CASCADE
);


CREATE TABLE INFORMES_FORENSES (
    id SERIAL PRIMARY KEY,
    adn_positivo CHAR(1) DEFAULT 'N',
    nivel_riesgo INTEGER NOT NULL,
    conclusion VARCHAR(100) NOT NULL,
    muestra_id INT UNIQUE NOT NULL,
    autor_examen VARCHAR(150) NOT NULL,

    CONSTRAINT CH_INFORMES_FORENSES_ADN_POSITIVO CHECK (adn_positivo IN ('S', 'N')),
    CONSTRAINT fk_detalle_satelite_satelites
        FOREIGN KEY (muestra_id)
        REFERENCES MUESTRAS_FORENSES(id)
        ON DELETE CASCADE
);
