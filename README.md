
# Distributed Testing Sessions for AutoTest

## Structure

The project is splitted into several sub-projects:
- Frontend is the interface component between DTSA and the user. It provides a command-line interface.
- Mapper is the centralized interface between the Frontend and the cloud providers.
- Mapped is the component executed on the cloud instances.
- java_utils gathers abstractions and services for Mapped and Mapper components

Java sub-projects use Maven as dependency manager and the "void-safe" facilities from Eclipse IDE.
In order to allow an IDE decoupling, the "void-safe" Eclipse annotations are replaced with custom annotations available in `dtsa.util.annotation'.
Aliasing is provided in the Eclipse project file.

### Frontend

The frontend component is written in Eiffel.
Requests and their responses are represented by objects exchanged over the network using the JSON format.
Object type is kept thanks to an object wrapping that adds a type label.


### Mapper

The component uses the request/processor architecture.
Request and response represent the domain (dtsa.mapper.client) and is decoupled of the cloud provider implementation provided as a processor (dtsa.mapper.cloud)
Only one cloud provider is implemented (Amazon Web Services)
Communication is decoupled from other application part thanks to the use of Inversion of Control (IoC).

The component can be easily configured via resource configuration files (available in src/main/resource/configurations)
- server.json file specifies the server configuration (port, logging) of the mapper interacting with the Frontend.
- aws.json specifies the AWS settings.
- service.json specifies the configuration (port) of the mapper interacting with the cloud instances.

### Mapped

The component communicates with the mapper and launches an AutoTest session.
On the Cloud instance it is used as a daemon service.
Use the script available in service/bin/script.bat to turn the mapped jar into a windows service.

## Set-up

Help for installing Windows Server instance or Ubuntu instance is available at:
- https://docs.google.com/document/d/1lsgwF5iFS1nGzfA2auY2w1a4p4HyqoFIkkKHBR1E4mI/edit?usp=sharing
- https://docs.google.com/document/d/1xbNe86azE8cqKQrNemo9vIlAIyXZXw2dzZA-reUkZ4A/edit?usp=sharing

## Results

Results are available at https://docs.google.com/spreadsheets/d/1ZVFbyamfFYeIyB4VcPC1X4GwyOjOZEMMKOhbvXW3xb4/edit?usp=sharing

The report is available [here](http://se.inf.ethz.ch/student_projects/victorien_elvinger/master_thesis_victorien_elvinger.pdf).


