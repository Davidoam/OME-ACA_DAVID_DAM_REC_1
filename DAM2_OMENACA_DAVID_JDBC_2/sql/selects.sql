SELECT * FROM CENTROS_FORENSES;

SELECT * FROM MUESTRAS_FORENSES;

SELECT * FROM INFORMES_FORENSES;

SELECT 
    m.id AS muestra_id,
    m.nombre AS muestra_nombre,
    m.codigo_caso,
    m.tipo_muestra,
    m.fecha_lanzamiento,
    m.estado_custodia,

    c.id AS centro_id,
    c.nombre AS centro_nombre,
    c.pais,
    c.nivel_seguridad,

    i.id AS informes_id,
    i.adn_positivo,
    i.nivel_riesgo,
    i.conclusion,

FROM MUESTRAS_FORENSES m
INNER JOIN CENTROS_FORENSES c
    ON c.centro_id = c.id
INNER JOIN INFORMES_FORENSES i
    ON m.muestra_id = m.id;