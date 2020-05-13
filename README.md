# passwordUI
Password UI

## Requirements
Oracle Java 1.8 - Must include JavaFX, currently Oracle Java 1.8 includes JavaFX in the /jre/lib/ext folder jfxrt.jar. 


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

## Build a release
To create a release and upload to github.

`cd target/jfx/app`
`tar -czvf ./myPasswords.tar.gz ./myPasswords-jfx.jar ./lib`

Verifiy the tar.
`tar -ztvf ./myPasswords.tar.gz`

