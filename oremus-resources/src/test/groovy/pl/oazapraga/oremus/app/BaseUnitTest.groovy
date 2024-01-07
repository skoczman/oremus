package pl.oazapraga.oremus.app

import pl.oazapraga.oremus.app.precondition.Precondition
import spock.lang.Specification

class BaseUnitTest extends Specification{
    def given = new Precondition()
}
