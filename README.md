# Machine Service Rest API Exercise

A machine service REST API using SpringBoot. 

The basic entitiy of service is the Machine entity. 
* name: String - must be less than or equal to 40 characters
* description: String
* throughputMins: Integer - machine throughput per mins. Must be a positive integer.

The REST services supports the following operations
- GET: /machines 
     * get all existing machine records from the service stoarge
- GET: /machine*s*/{name}
     * get all machines that have the requested name
- GET: /machine/{id}
     * get machine with an unique id
- POST: /machine/add, with request body containing the a new machine entity
     * create new machine with the supplied parameters. 
- POST: /machine/{id}, with some of the fields of the entity field supplied
     * update existing machine with the given id.
- PUT: /machine/{id}, with a new machine entity information
     * upsert machine.

Assumptions
- getById will return 404 when machine is not found
- getByName/getAll will return empty list when result is empty
- updating existing machine must provide a valid id
- upserting will create a new machine with the inputs if the id does not exist

Limitations
- No authorization/authentications enabled 
- No caching enabled
- service is running with in memory database. Data will be cleared on restart