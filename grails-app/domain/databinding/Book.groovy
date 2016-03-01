package databinding

class Book {

	String title

	static belongsTo = [author: Author]

    static constraints = {
    	title nullable: false
    }

    String toString() {
    	"${title}"
    }
}
