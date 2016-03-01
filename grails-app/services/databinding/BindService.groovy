package databinding

import grails.converters.*
import grails.transaction.Transactional

class BindService {

    def storeAuthor(reqMap) {
    	log.info "StoreAuthor(${reqMap})"

		//sleep(5)

		def result = Author.withTransaction { status ->
			try {
				def author = new Author(reqMap)
				if (author.hasErrors()) {
				    author.errors.each {
				        log.error it
				    }
				}				
				author.save()
				JSON.use('deep') {
					author as JSON
				}
			}
			catch (e) {
				log.error "Exception ${e.name}", e
				status.setRollbackOnly()
				e
			}
		}
     	log.info "Result: $result"
    }
}
