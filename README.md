# Duck Duck Goose
<img src="https://raw.githubusercontent.com/jgardella/duck-duck-goose/master/src/main/resources/image/ddg_logo.png" alt="duck duck goose logo" width="200">
Duck Duck Goose is an application for determing active members of a club, based on `.xlsx` attendance sheets and given requirements for active membership. Written specifically for use at Stevens Institute of Technology.

## Installation and Usage
[Grab the latest release](https://github.com/jgardella/duck-duck-goose/releases), and [see the wiki for info on how to use DDG](https://github.com/jgardella/duck-duck-goose/wiki/Using-Duck-Duck-Goose) (though it should hopefully be pretty self-explanatory).


## Building
DDG uses Maven to build, with the JavaFX Maven plugin. A jar can be built using:

```
mvn jfx:jar
```

## Testing
Run JUnit tests using Maven:

```
mvn test
```

The JUnit tests only cover the backend (and currently do quite a poor job of it). Run the following command to run the full DDG GUI and perform manual testing:

```
mvn jfx:run
```

## Thanks
Big thanks to [Frank DiCola](http://www.yoshgunn.com) for the logo.

## License

Licensed under the MIT license.
