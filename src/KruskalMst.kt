import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class KruskalMst() {
    //Find Maximum Spacing With Number of Clusters Given
    var nodeRootMapMaxSpace = HashMap<Int, Node>()

    fun calcMaxSpacing(numOfGroupsToCluster: Int, nodeRootMap: HashMap<Int, Node>, edgeHeap: PriorityQueue<Edge>): Int {
        var groupsClustered = nodeRootMap.size
        while (groupsClustered > numOfGroupsToCluster) {
            var edge = edgeHeap.poll()
            var nodeOne = edge.nodeOne
            var nodeTwo = edge.nodeTwo
            var nodeOneRoot = find(nodeOne, nodeRootMap)
            var nodeTwoRoot = find(nodeTwo, nodeRootMap)
            if (nodeOneRoot != nodeTwoRoot) {
                union(nodeOneRoot, nodeTwoRoot, nodeRootMap)
                groupsClustered--
            }
        }
        var maxSpacingHeap = PriorityQueue<Int>()
        while (edgeHeap.isNotEmpty()) {
            var edge = edgeHeap.poll()
            if (find(edge.nodeOne, nodeRootMap) != find(edge.nodeTwo, nodeRootMap)) {
                maxSpacingHeap.add(edge.weight)
            }
        }
        return maxSpacingHeap.poll()
    }

    private fun find(nodeId: Int, nodeRootMap: HashMap<Int, Node>): Int {
        var node = nodeRootMap.getValue(nodeId)
        if (nodeId == node.root) {
            return nodeId
        } else {
            var root = find(node.root, nodeRootMap)
            node.root = root
            return root
        }
    }

    private fun union(nodeOneRoot: Int, nodeTwoRoot: Int, nodeRootMap: HashMap<Int, Node>) {
        var nodeOneRootRank = nodeRootMap.getValue(nodeOneRoot).rank
        var nodeTwoRootRank = nodeRootMap.getValue(nodeTwoRoot).rank
        if (nodeOneRootRank == nodeTwoRootRank) {
            merge(nodeOneRoot, nodeTwoRoot, nodeRootMap)
            nodeRootMap.getValue(nodeOneRoot).rank++
        } else {
            var largerRankNode = Math.max(nodeOneRootRank, nodeTwoRootRank)
            if (largerRankNode == nodeOneRootRank) {
                merge(nodeOneRoot, nodeTwoRoot, nodeRootMap)
            } else {
                merge(nodeTwoRoot, nodeOneRoot, nodeRootMap)
            }
        }
    }

    private fun merge(nodeRootReceived: Int, nodeRootChanged: Int, nodeRootMap: HashMap<Int, Node>) {
        nodeRootMap.getValue(nodeRootChanged).root = nodeRootReceived
    }

    //Find Number of Clusters With Minimum Distance Given
    fun calcNumOfClusters(nodeRootMap: HashMap<String, Node>, bitsSet: HashSet<String>): Int {
        var numGroupsClustered = bitsSet.size
        while (bitsSet.isNotEmpty()) {
            var origBits = bitsSet.first()
            bitsSet.remove(origBits)
            var minDistHashSet = generateDistSets(origBits.toCharArray())
            for (possBits in minDistHashSet) {
                if (bitsSet.contains(possBits)) {
                    var origBitsRoot = find(origBits, nodeRootMap)
                    var possBitsRoot = find(possBits, nodeRootMap)
                    if (!origBitsRoot.equals(possBitsRoot)) {
                        union(origBitsRoot, possBitsRoot, nodeRootMap)
                        bitsSet.remove(origBits)
                        numGroupsClustered--
                    }
                }
            }
        }

        return numGroupsClustered
    }

    //generate bits with Hamming distance of 1 and 2
    private fun generateDistSets(binarySet: CharArray): HashSet<String> {
        var minDistHashSet = HashSet<String>()
        for (d in 0..binarySet.size - 1) {
            var newBinarySet = binarySet.copyOf()
            newBinarySet[d] = flipDigit(newBinarySet[d])
            minDistHashSet.add(newBinarySet.joinToString(""))
            for (e in 0..binarySet.size - 1) {
                if (e != d) {
                    var newBinarySetTwo = binarySet.copyOf()
                    newBinarySetTwo[d] = flipDigit(newBinarySetTwo[d])
                    newBinarySetTwo[e] = flipDigit(newBinarySetTwo[e])
                    minDistHashSet.add(newBinarySetTwo.joinToString(""))
                }
            }
        }
        return minDistHashSet
    }

    private fun flipDigit(c: Char): Char {
        if (c == "0".single()) {
            return "1".single()
        } else {
            return "0".single()
        }
    }

    private fun find(nodeId: String, nodeRootMap: HashMap<String, Node>): String {
        var node = nodeRootMap.getValue(nodeId)
        if (nodeId.equals(node.stringRoot)) {
            return nodeId
        } else {
            var root = find(node.stringRoot, nodeRootMap)
            node.stringRoot = root
            return root
        }
    }

    private fun union(nodeOneRoot: String, nodeTwoRoot: String, nodeRootMap: HashMap<String, Node>) {
        var nodeOneRootRank = nodeRootMap.getValue(nodeOneRoot).rank
        var nodeTwoRootRank = nodeRootMap.getValue(nodeTwoRoot).rank
        if (nodeOneRootRank == nodeTwoRootRank) {
            merge(nodeOneRoot, nodeTwoRoot, nodeRootMap)
            nodeRootMap.getValue(nodeOneRoot).rank++
        } else {
            var largerRankNode = Math.max(nodeOneRootRank, nodeTwoRootRank)
            if (largerRankNode == nodeOneRootRank) {
                merge(nodeOneRoot, nodeTwoRoot, nodeRootMap)
            } else {
                merge(nodeTwoRoot, nodeOneRoot, nodeRootMap)
            }
        }
    }

    private fun merge(nodeRootReceived: String, nodeRootChanged: String, nodeRootMap: HashMap<String, Node>) {
        nodeRootMap.getValue(nodeRootChanged).stringRoot = nodeRootReceived
    }

}
