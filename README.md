
# CHALLENGE - JUEGO DE CARROS  
## Resumen 
En este reto vamos a desarrollar un concurso de carros, donde vamos a tener ciertas reglas del juego. Para este reto es necesario tener los conocimientos básicos de DESARROLLO WEB (FRONTEND Y BACKEND). Esto implica conocer el modelamiento de objetos, además se deberá guardar en base de datos los resultados del juego usando MongoDB y Un Broker de mensajeria. 

Dentro del reto se debe considerar lo siguiente: 
- Manejo de clases u objetos 
- Persistencia de datos 
- Manejos de listas o colecciones 
- Conocimiento de DDD. 
- Manejo de Datos con MongoDB
- Manejo de Broker de Mensajeria
- Conocimiento de Angular 

Sólo aplícate al reto si te sientes capaz de hacerlo. 
¡Buena suerte! 


## Funcionalidades 
- [10pts]Configurar Juego: Crear juego con jugadores, el juego debe tener los limites de kilómetros por cada pista (un jugador puede ser un conductor y un conductor debe tener un carro asociado y un carro debe estar asociado a un carril que a su vez debe estar en una pista)
- [20pts]Iniciar el juego: iniciar con un identificador del juego, se debe tener la lista de carros en donde se pueda iterar y avanzar según la posición de la pista o carril, esto debe ser de forma aleatoria (por medio del dado). 
- [20pts]Asignar podio (fin del juego): Se debe seleccionar primer, segundo y tercer lugar en la medida que los carros llegan a la meta (asignar al podio). 
- [15pts]Guardar datos: Se debe persistir los resultados con los nombres de los conductores en la posición del podio y agregar un contador de las veces que ha ganado
- [15pts]Tener un login/logout: se debe iniciar sesión con google o con usuario y contraseña
- [30pts]Logs de score: Tener una vista que me permita ver los mejores resultados almacenados del juego

## Reto del Backend
- Poner en marcha el backend
- Crear un nuevo servicio para consultar los logs (nuevo entrypoint)
- Cambiar el broker de nats por rabbitmq
- Desplegar en Heroku usando Docker

## Reto del Frontend
- Crear una interfaz que puedan usar los servcios
- Crear un sistema de navegación y una estructura adecuada
- Hacer despliegue del frontend en firebase


## ¿Cómo se prueba?

Inicar los siguientes servicios:

`docker run -d --name nats -p 4222:4222 nats`

`docker run -d --name mongodb  -p 27017:27017 -v $HOME/data:/data/db mongo:3`

### Ejemplo de como crear el juego
`
curl -X POST localhost:8080/crearJuego -H 'Content-Type: application/json' -d '{ "kilometros": 3, "juegoId":"xyz", "jugadores": { "112233": "Camilo andres", "4455443": "Pedro", "fffff": "Santiago" } }'
`

### Ejemplo de como iniciar el juego
`
curl -X POST localhost:8080/iniciarJuego -H 'Content-Type: application/json' -d '{ "juegoId":"xyz" }'
`

## IMPORTANTE:

1. Debes entregar la solución que satisfaga los 100pts para un excelente un 75pts para pasar, por debajo de los 75pts - 65pts se puede habilitar. Por debajo de 65pts se debe negociar y revisar detenidamente para dar posibilidad de habilitación.
2. Se debe entregar el código y la URL con el funcionamiento del mismo
3. MAXIMO de entrega hasta el Domingo
