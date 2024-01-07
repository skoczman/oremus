package pl.oazapraga.oremus.app.reflection

class ReflectionDto {

    data class ReflectionCreateRequest(val title: String?, val text: String?)

    data class ReflectionResponse(val title: String?, val text: String?)
}