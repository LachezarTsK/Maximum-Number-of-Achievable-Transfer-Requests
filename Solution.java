
public class Solution {

    private static final int NOT_FOUND = -1;
    private static final int BALANCED = 0;

    public int maximumRequests(int numberOfBuildings, int[][] transferRequests) {
        int numberOfBalanceChangingTransferRequests = moveBalanceChangingTransferRequestsToTheFront(transferRequests);
        int numberOfNonbalanceChangingTransferRequests = transferRequests.length - numberOfBalanceChangingTransferRequests;

        int maxNumberOfAchievableTransferRequests = 0;
        int upperLimit = (int) Math.pow(2, numberOfBalanceChangingTransferRequests);

        for (int i = 1; i < upperLimit; ++i) {

            int value = i;
            int index = 0;
            int numberOfRequests = 0;
            int[] balance = new int[numberOfBuildings];

            while (value > 0) {
                if ((value & 1) == 1) {
                    int from = transferRequests[index][0];
                    int to = transferRequests[index][1];
                    --balance[from];
                    ++balance[to];
                    ++numberOfRequests;
                }
                value >>= 1;
                ++index;
            }

            if (maxNumberOfAchievableTransferRequests < numberOfRequests && allBuildingsAreBalanced(balance)) {
                maxNumberOfAchievableTransferRequests = numberOfRequests;
            }
        }

        maxNumberOfAchievableTransferRequests += numberOfNonbalanceChangingTransferRequests;
        return maxNumberOfAchievableTransferRequests;
    }

    private boolean allBuildingsAreBalanced(int[] balance) {
        for (int current : balance) {
            if (current != BALANCED) {
                return false;
            }
        }
        return true;
    }

    private int moveBalanceChangingTransferRequestsToTheFront(int[][] transferRequests) {
        int indexNonchangingBalanceTransferRequests = NOT_FOUND;
        for (int i = 0; i < transferRequests.length; ++i) {
            if (transferRequests[i][0] == transferRequests[i][1]) {
                indexNonchangingBalanceTransferRequests = i;
                break;
            }
        }

        if (indexNonchangingBalanceTransferRequests == NOT_FOUND) {
            return transferRequests.length;
        }

        for (int i = indexNonchangingBalanceTransferRequests + 1; i < transferRequests.length; ++i) {
            if (transferRequests[i][0] != transferRequests[i][1]) {
                transferRequests[indexNonchangingBalanceTransferRequests] = transferRequests[i];
                ++indexNonchangingBalanceTransferRequests;
            }
        }
        return indexNonchangingBalanceTransferRequests;
    }
}
