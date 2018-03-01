import java.io.File
import java.util.*
import kotlin.collections.HashSet

object KruskalMstTest {
    @JvmStatic
    fun main(args: Array<String>) {
        //Find Maximum Spacing With Number of Clusters Given
        var numGroupsToCluster = 4
        var edgeHeap = PriorityQueue<Edge>()
        var nodeMapFindMaxSpacing = HashMap<Int, Node>()
        var rootNodesSet = HashSet<Int>()
        File("src/data/MaxDistAtKData.txt")
                .inputStream()
                .bufferedReader()
                .readLines()
                .forEach { it ->
                    var line = Regex("[^\\s]+").findAll(it)
                    var inputList = arrayListOf<Int>()
                    for (num in line) {
                        inputList.add(num.value.toInt())
                    }
                    if (inputList.size > 1) {
                        var nodeOne = inputList[0]
                        var nodeTwo = inputList[1]
                        var weight = inputList[2]
                        nodeMapFindMaxSpacing.put(nodeOne, Node(nodeOne, nodeOne, 0))
                        nodeMapFindMaxSpacing.put(nodeTwo, Node(nodeTwo, nodeTwo, 0))
                        edgeHeap.add(Edge(nodeOne, nodeTwo, weight))
                        rootNodesSet.add(nodeOne)
                        rootNodesSet.add(nodeTwo)
                    }
                }
        println(String.format("Max Spacing: %1s", KruskalMst().calcMaxSpacing(numGroupsToCluster, nodeMapFindMaxSpacing, edgeHeap)))

        //Find Number of Clusters With Minimum Distance Given
        var nodeMapFindNumClusters = HashMap<String, Node>()
        var bitsSet = HashSet<String>()
        var firstLine = true
        File("src/data/NumClustersWithMinDistData.txt")
        //File("src/data/NumClustersWithMinDistData_70_8192.txt")
                .inputStream()
                .bufferedReader()
                .readLines()
                .forEach({ it ->
                    var line = Regex("[^\\s]").findAll(it)
                    var bits = ""
                    for (num in line) {
                        bits += num.value
                    }
                    if (!firstLine) {
                        if(!bitsSet.contains(bits)) {
                            nodeMapFindNumClusters.put(bits, Node(bits, bits, 0))
                            bitsSet.add(bits)
                        }
                    } else {
                        firstLine = false
                    }
                })
        println(String.format("Number of Clusters With Min 2 Distance:%1s",
                KruskalMst().calcNumOfClusters(nodeMapFindNumClusters, bitsSet)))
    }
}