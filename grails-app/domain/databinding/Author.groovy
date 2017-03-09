package databinding

class Author {

	String name

	static hasMany = [books: Book]

    static constraints = {
    	name nullable: false
    }

    String toString() {
    	"$name(${books?.size()})"
    }
}
