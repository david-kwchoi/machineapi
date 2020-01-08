# Machine Service Rest API Exercise

This is a project for demonstrating a machine service resti API using SpringBoot. For simplicity it's using H2 inmemory database.

The basic entitiy of service is the Machine entity. 
* name: String - must be less than or equal to 40 characters
* description: String
* throughputMins: Integer - machine throughput per mins. Must be a positive integer.

The REST services supports the following operations
- GET: /machines 
     * get all existing machine records from the service stoarge
- GET: /machines/{name}
     * get all machines that have the requested name
- GET: /machine/{id}
     * get machine with an unique id
- POST: /machine/add, with request body containing the a new machine entity
     * create new machine with the supplied parameters. 
- POST: /machine/{id}, with some of the fields of the entity field supplied
     * update existing machine.
- PUT: /machine/{id}, with a new machine entity information
     * replace existing machine.

Limitations
- No authentications required for crud operations
- No caching enabled
- service is running with in memory databse