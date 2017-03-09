package databinding

import grails.transaction.Transactional

import static grails.async.web.WebPromises.*

import grails.converters.*
import grails.async.Promise

class BindController {

	def bindService

    def index() { }

    @Transactional
    def addDirect() {
        def reqMap = [:]
        request.withFormat {
            xml {
                log.info "request as XML"
                reqMap = request.XML as Map
            }
            json {
                log.info "request as JSON\n${request.JSON}"
                reqMap = request.JSON as Map
            }
        }

        log.info "{}", reqMap

        sleep(15000)

        def author = new Author(reqMap)
        author.save()

        JSON.use('deep') {
            render author as JSON
        }
    }

    def addWithPromise() {
    	def reqMap = [:]
    	request.withFormat {
    		xml {
    			log.info "request as XML"
    			reqMap = request.XML as Map
    		}
    		json {
    			log.info "request as JSON\n${request.JSON}"
    			reqMap = request.JSON as Map
    		}
    	}

    	log.info "{}", reqMap

    	Promise p = task {
    		bindService.storeAuthor(reqMap)
    	}
    	p.onError {
    		log.error "${it.name}", it
    	}
    	p.onComplete {
    		log.info "{}", it
            render it
        }
    }

	def addInService() {
        def reqMap = [:]
        request.withFormat {
            xml {
                log.info "request as XML"
                reqMap = request.XML as Map
            }
            json {
                log.info "request as JSON\n${request.JSON}"
                reqMap = request.JSON as Map
            }
        }

        log.info "{}", reqMap

        bindService.storeAuthorWithPromise(reqMap)
    }
}
