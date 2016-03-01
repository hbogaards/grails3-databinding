package databinding

import static grails.async.Promises.*

import grails.converters.*
import grails.async.Promise

class BindController {

	def bindService

    def index() { }

    def add() {
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

    	log.info reqMap

    	def author = new Author(reqMap)
    	author.save()

    	Promise p = task {
    		bindService.storeAuthor(reqMap)
    	}
    	p.onError {
    		log.error "${it.name}", it
    	}
    	p.onComplete {
    		log.info it
    	}

		JSON.use('deep') {
        	render author as JSON
    	}    	
    }
}
