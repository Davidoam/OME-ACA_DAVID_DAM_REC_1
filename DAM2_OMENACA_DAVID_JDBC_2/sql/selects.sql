SELECT * FROM CENTROS_FORENSES;

SELECT * FROM MUESTRAS_FORENSES;

SELECT * FROM INFORMES_FORENSES;


SELECT 
    m.id AS muestra_id,
    m.codigo_caso,
    m.tipo_muestra,
    m.fecha_recogida,
    m.estado_custodia,

    c.id AS centro_id,
    c.nombre AS agencia_nombre,
    c.pais AS agencia_pais,
    c.nivel_seguridad,

FROM MUESTRAS_FORENSES m
INNER JOIN CENTROS_FORENSES c
    ON m.centro_id = c.id;


SELECT 
    m.id AS muestra_id,
    m.codigo_caso,
    m.tipo_muestra,
    m.fecha_recogida,
    m.estado_custodia,

    c.id AS centro_id,
    c.nombre AS agencia_nombre,
    c.pais AS agencia_pais,
    c.nivel_seguridad,

    i.id AS informe_id,
    i.adn_positivo,
    i.nivel_riesgo,
    i.conclusion

FROM MUESTRAS_FORENSES m
INNER JOIN CENTROS_FORENSES c
    ON m.centro_id = c.id
INNER JOIN INFORMES_FORENSES i
    ON i.muestra_id = m.id;

SELECT adn_positivo, nivel_riesgo, pais

FROM INFORMES_FORENSES i
INNER JOIN MUESTRAS_FORENSES 
    ON i.muestra_id = m.id;
INNER JOIN CENTROS_FORENSES c
    ON c.centro_id = c.id;
