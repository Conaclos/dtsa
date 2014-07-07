
# Distributed Testing Sessions for AutoTest


## Mapper

Mapper component use Maven to manage its dependencies.

The project is developed with Eclipse and its "void-safe" facilities.
To allow an IDE decoupling, the "void-safe" Eclipse annotations are replaced with custom annotations available in `dtsa.mapper.util.annotation'.

- `dtsa.mapper.base': Application base allowing to launch application and client sessions, and to inject dependencies from `dtsa.mapper.cloud' to `dtsa.mapper.client'.
- `dtsa.mapper.client': Part which defines requests and responses objects, and defines processor abstraction.
- `dtsa.mapper.cloud': Rely on `dtsa.mapper.client' requests, response and processor abstraction.
- `dtsa.mapper.util': various stuff.

Application's configurations are available in './src/main/resources/configurations/'.

Tests are available in './src/tests'.

