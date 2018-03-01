class Node(var id: Int, var root: Int, var rank: Int) {

    var stringId: String = ""
    var stringRoot: String = ""

    constructor(stringId: String, stringRoot: String, rank: Int): this(0, 0, rank){
        this.stringId = stringId
        this.stringRoot = stringRoot
    }

    override fun equals(other: Any?): Boolean {
        other as Node

        if (id == other.id || stringId.equals(other.stringId)) {
            return true
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        return 31 * id.hashCode()
    }
}