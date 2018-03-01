class Edge(var nodeOne: Int, var nodeTwo: Int, var weight: Int) : Comparable<Edge> {
    override fun equals(other: Any?): Boolean {
        other as Edge
        if ((nodeOne == other.nodeOne && nodeTwo == other.nodeTwo)
                || (nodeOne == other.nodeTwo && nodeTwo == other.nodeOne))
            return true
        else
            return false
    }

    override fun hashCode(): Int {
        return 31 * (nodeOne + nodeTwo)
    }

    override fun compareTo(other: Edge): Int {
        if (weight > other.weight)
            return 1
        else
            return -1
    }
}
