package databinding

import static grails.async.Promises.*

import grails.async.Promise

import grails.converters.*
import grails.transaction.Transactional

class BindService {

    @Transactional
    def storeAuthorWithPromise(reqMap) {
    	log.info "StoreAuthorWithPromise(${reqMap})"

        Promise p = task {
            sleep(15000)

            def result = Author.withTransaction {
                def author = new Author(reqMap)
                if (author.hasErrors()) {
                    author.errors.each {
                        log.error "{}", it
                    }
                }
                author.save()
                JSON.use('deep') {
                    return author as JSON
                }
            }
            return result
		}
        p.onError {
            log.error "${it.name}", it
        }
        p.onComplete {
            log.info "{}", it
        }
    }

    @Transactional
    def storeAuthor(reqMap) {
        log.info "StoreAuthor(${reqMap})"

        sleep(15000)

        def author = new Author(reqMap)
        if (author.hasErrors()) {
            author.errors.each {
                log.error "{}", it
            }
        }
        author.save()
        JSON.use('deep') {
            author as JSON
        }
    }
}
