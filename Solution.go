
package main
import "math"

const NOT_FOUND = -1
const BALANCED = 0

func maximumRequests(numberOfBuildings int, transferRequests [][]int) int {
    numberOfBalanceChangingTransferRequests := moveBalanceChangingTransferRequestsToTheFront(transferRequests)
    numberOfNonbalanceChangingTransferRequests := len(transferRequests) - numberOfBalanceChangingTransferRequests

    maxNumberOfAchievableTransferRequests := 0
    upperLimit := int(math.Pow(2.0, float64(numberOfBalanceChangingTransferRequests)))

    for i := 1; i < upperLimit; i++ {

        value := i
        index := 0
        numberOfRequests := 0
        balance := make([]int, numberOfBuildings)

        for value > 0 {
            if (value & 1) == 1 {
                from := transferRequests[index][0]
                to := transferRequests[index][1]
                balance[from]--
                balance[to]++
                numberOfRequests++
            }
            value >>= 1
            index++
        }

        if maxNumberOfAchievableTransferRequests < numberOfRequests && allBuildingsAreBalanced(balance) {
            maxNumberOfAchievableTransferRequests = numberOfRequests
        }
    }

    maxNumberOfAchievableTransferRequests += numberOfNonbalanceChangingTransferRequests
    return maxNumberOfAchievableTransferRequests
}

func allBuildingsAreBalanced(balance []int) bool {
    for _, current := range balance {
        if current != BALANCED {
            return false
        }
    }
    return true
}

func moveBalanceChangingTransferRequestsToTheFront(transferRequests [][]int) int {
    indexNonchangingBalanceTransferRequests := NOT_FOUND
    for i := range transferRequests {
        if transferRequests[i][0] == transferRequests[i][1] {
            indexNonchangingBalanceTransferRequests = i
            break
        }
    }

    if indexNonchangingBalanceTransferRequests == NOT_FOUND {
        return len(transferRequests)
    }

    for i := indexNonchangingBalanceTransferRequests + 1; i < len(transferRequests); i++ {
        if transferRequests[i][0] != transferRequests[i][1] {
            transferRequests[indexNonchangingBalanceTransferRequests] = transferRequests[i]
            indexNonchangingBalanceTransferRequests++
        }
    }
    return indexNonchangingBalanceTransferRequests
}
