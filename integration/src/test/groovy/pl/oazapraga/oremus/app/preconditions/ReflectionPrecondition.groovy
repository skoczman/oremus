package pl.oazapraga.oremus.app.preconditions

import pl.oazapraga.oremus.app.dto.TestNewReflection

class ReflectionPrecondition {

    static unsaved(String text = "Example of reflection text.") {
        return new TestNewReflection(text: text)
    }

}

