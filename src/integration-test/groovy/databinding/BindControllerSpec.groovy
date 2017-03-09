package databinding

import geb.spock.GebSpec
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback

@Rollback
@Integration
class BindControllerSpec extends GebSpec {

    static final AUTHOR = '{"books":[{"title":"The Firm"}],"name":"John Grisham"}'

    void "testAddDirect"() {
        given:
        RestBuilder rest = new RestBuilder()

        when:
        RestResponse response = rest.post("http://localhost:${serverPort}/bind/addDirect") {
            json(AUTHOR)
        }

        then:
        response.status == 200
        response.text == '{"id":1,"books":[{"id":1,"author":{"_ref":"../..","class":"databinding.Author"},"title":"The Firm"}],"name":"John Grisham"}'
        rest.get("http://localhost:${serverPort}/author/show/1.json").text == '{"id":1,"books":[{"id":1}],"name":"John Grisham"}'
    }

    void "testAddWithPromise"() {
        given:
        RestBuilder rest = new RestBuilder()

        when:
        RestResponse response = rest.post("http://localhost:${serverPort}/bind/addWithPromise") {
            json(AUTHOR)
        }

        then:
        response.status == 200
        response.text == '{"id":2,"books":[{"id":2,"author":{"_ref":"../..","class":"databinding.Author"},"title":"The Firm"}],"name":"John Grisham"}'
        rest.get("http://localhost:${serverPort}/author/show/2.json").text == '{"id":2,"books":[{"id":2}],"name":"John Grisham"}'
    }

    void  "testAddInService"() {
        given:
        RestBuilder rest = new RestBuilder()

        when:
        RestResponse response = rest.post("http://localhost:${serverPort}/bind/addInService") {
            json(AUTHOR)
        }

        then:
        response.status == 200
        rest.get("http://localhost:${serverPort}/author/show/3.json").text == '{"id":3,"books":[{"id":3}],"name":"John Grisham"}'
    }
}
