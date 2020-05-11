# passwordUI
Password UI


## Run the project

`mvn clean jfx:run`


## Build the projet to a app.

`mvn jfx:jar`

The files will be placed in,
`./target/jfx/app`

There is also a native build, not sure yet what that is to do.
you must add a vendor in the pom.xml in the plugin config.
<vendor>YourCompany</vendor>

`mvn jfx:native`

The file will be placed in.
`./target/jfx/native`

