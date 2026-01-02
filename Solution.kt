
import kotlin.math.pow

class Solution {

    private companion object {
        const val NOT_FOUND = -1
        const val BALANCED = 0
    }

    fun maximumRequests(numberOfBuildings: Int, transferRequests: Array<IntArray>): Int {
        val numberOfBalanceChangingTransferRequests = moveBalanceChangingTransferRequestsToTheFront(transferRequests)
        val numberOfNonbalanceChangingTransferRequests = transferRequests.size - numberOfBalanceChangingTransferRequests

        var maxNumberOfAchievableTransferRequests = 0
        val upperLimit = (2.0).pow(numberOfBalanceChangingTransferRequests).toInt()

        for (i in 1..<upperLimit) {

            var value = i
            var index = 0
            var numberOfRequests = 0
            val balance = IntArray(numberOfBuildings)

            while (value > 0) {
                if ((value and 1) == 1) {
                    val from = transferRequests[index][0]
                    val to = transferRequests[index][1]
                    --balance[from]
                    ++balance[to]
                    ++numberOfRequests
                }
                value = value shr 1
                ++index
            }

            if (maxNumberOfAchievableTransferRequests < numberOfRequests && allBuildingsAreBalanced(balance)) {
                maxNumberOfAchievableTransferRequests = numberOfRequests
            }
        }

        maxNumberOfAchievableTransferRequests += numberOfNonbalanceChangingTransferRequests
        return maxNumberOfAchievableTransferRequests
    }

    private fun allBuildingsAreBalanced(balance: IntArray): Boolean {
        for (current in balance) {
            if (current != BALANCED) {
                return false
            }
        }
        return true
    }

    private fun moveBalanceChangingTransferRequestsToTheFront(transferRequests: Array<IntArray>): Int {
        var indexNonchangingBalanceTransferRequests = NOT_FOUND
        for (i in transferRequests.indices) {
            if (transferRequests[i][0] == transferRequests[i][1]) {
                indexNonchangingBalanceTransferRequests = i
                break
            }
        }

        if (indexNonchangingBalanceTransferRequests == NOT_FOUND) {
            return transferRequests.size
        }

        for (i in indexNonchangingBalanceTransferRequests + 1..<transferRequests.size) {
            if (transferRequests[i][0] != transferRequests[i][1]) {
                transferRequests[indexNonchangingBalanceTransferRequests] = transferRequests[i]
                ++indexNonchangingBalanceTransferRequests
            }
        }
        return indexNonchangingBalanceTransferRequests
    }
}
