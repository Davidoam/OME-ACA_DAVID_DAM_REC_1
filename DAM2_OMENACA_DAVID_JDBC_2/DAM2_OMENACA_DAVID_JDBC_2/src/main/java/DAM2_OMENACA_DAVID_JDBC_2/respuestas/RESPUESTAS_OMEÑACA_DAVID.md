
PREGUNTA 1

Explica cómo funciona la relación 1:N entre CentroForense y MuestraForense tanto en SQL como en Java.

La relación 1:N entre CentroForense y MuestraForense, en SQL, hacemos una relación de uno a muchos, Tabla padre (CENTROS_FORENSES) almacena los datos relevantes de centros_forenses y utiliza una clave primaria id.
Tabla hija (MUESTRAS_FORENSES) almacena la lista de muestras. Contiene una clave foránea centro_id, vinculada a la tabla padre.
En java, al no poder hacer relaciones como en SQL, tenemos que hacer esta relacion de uno a muchos pasando como Objeto private CentrosForenses centrosForenses; 

PREGUNTA 2

Explica por qué en Java utilizamos:

private CentroForense centro;

y no:

private int centroId;

Hacemos uso de private CentroForense centro;porque si bien es cierto que nos podría llegar a servir poner un int y el id de la agencia, no tendríamos los demás datos relevantes de la centro, que posteriormente nos pueden servir si tratamos con ellos.

PREGUNTA 3

Explica qué ventaja aporta PreparedStatement frente a concatenar SQL manualmente.

Tenemos una ventaja real al usar PreparedStatement si lo comparamos con concatenar SQL manualmente porque podemos evitar inyecciones SQL no deseadas, esto lo logramos gracas a las bind (?) que ponemos en lugar de los String correspondientes.