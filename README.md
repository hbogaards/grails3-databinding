# grails3-databinding
Grails 3 Databinding

This test application demonstrates the problem when creating a new Domain class instance from a Service call
from a `task` in a Controller.

After starting the app with `grails run-app --info` you can send the `author1.json` to `http://localhost:8080/bind/add` 
using `curl` or something equivalent.

When creating the Domain class in the `BindController` all is well.
When creating the Domain class in the `BindService` the result is an empty instance with a validation error
