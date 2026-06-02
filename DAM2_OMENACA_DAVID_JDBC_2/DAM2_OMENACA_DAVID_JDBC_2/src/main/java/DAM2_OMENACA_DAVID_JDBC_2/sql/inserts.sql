INSERT INTO CENTROS_FORENSES (nombre, pais, nivel_seguridad, autor_examen)
VALUES
('CENTRO-01', 'España', 99, 'OMENACA_AYUSO_DAVID_DAM2'),
('CENTRO-02', 'Portugal', 90, 'OMENACA_AYUSO_DAVID_DAM2'),
('CENTRO-03', 'Japón', 10, 'OMENACA_AYUSO_DAVID_DAM2'),
('CENTRO-04', 'Estados Unidos', 80, 'OMENACA_AYUSO_DAVID_DAM2'),
('CENTRO-05', 'Rusia', 60, 'OMENACA_AYUSO_DAVID_DAM2');

INSERT INTO MUESTRAS_FORENSES (codigo_caso, tipo_muestra, fecha_recogida, estado_custodia, centro_id, autor_examen)
VALUES
(001, 'Muestra-001', '1992-02-25', 'Retenido', 1, 'OMENACA_AYUSO_DAVID_DAM2'),
(002, 'Muestra-002', '2022-01-02', 'Custodiado', 2, 'OMENACA_AYUSO_DAVID_DAM2'),
(003, 'Muestra-003', '2024-04-04', 'Enviado', 3, 'OMENACA_AYUSO_DAVID_DAM2'),
(004, 'Muestra-004', '2026-05-11', 'Enviado', 4, 'OMENACA_AYUSO_DAVID_DAM2'),
(005, 'Muestra-005', '2020-06-22', 'Custodiado', 5, 'OMENACA_AYUSO_DAVID_DAM2');

INSERT INTO INFORMES_FORENSES (adn_positivo, nivel_riesgo, conclusion, muestra_id, autor_examen)
VALUES
('S', 70, 'Alto riesgo', 1, 'OMENACA_AYUSO_DAVID_DAM2'),
('S', 80, 'Muy alto riesgo', 2, 'OMENACA_AYUSO_DAVID_DAM2'),
('S', 70, 'Alto riesgo', 3, 'OMENACA_AYUSO_DAVID_DAM2'),
('N', 30, 'Muy bajo riesgo', 4, 'OMENACA_AYUSO_DAVID_DAM2'),
('N', 50, 'Bajo riesgo', 5, 'OMENACA_AYUSO_DAVID_DAM2');

