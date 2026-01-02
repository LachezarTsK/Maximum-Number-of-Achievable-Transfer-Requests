
#include <span>
#include <vector>
#include <algorithm>
using namespace std;

class Solution {

    static const int NOT_FOUND = -1;
    static const int BALANCED = 0;

public:
    int maximumRequests(int numberOfBuildings, vector<vector<int>>& transferRequests) const {
        int numberOfBalanceChangingTransferRequests = moveBalanceChangingTransferRequestsToTheFront(transferRequests);
        int numberOfNonbalanceChangingTransferRequests = transferRequests.size() - numberOfBalanceChangingTransferRequests;

        int maxNumberOfAchievableTransferRequests = 0;
        int upperLimit = pow(2, numberOfBalanceChangingTransferRequests);

        for (int i = 1; i < upperLimit; ++i) {

            int value = i;
            int index = 0;
            int numberOfRequests = 0;
            vector<int> balance(numberOfBuildings);

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

private:
    bool allBuildingsAreBalanced(span<const int> balance) const {
        for (int current : balance) {
            if (current != BALANCED) {
                return false;
            }
        }
        return true;
    }

    int moveBalanceChangingTransferRequestsToTheFront(span<vector<int>> transferRequests) const {
        int indexNonchangingBalanceTransferRequests = NOT_FOUND;
        for (int i = 0; i < transferRequests.size(); ++i) {
            if (transferRequests[i][0] == transferRequests[i][1]) {
                indexNonchangingBalanceTransferRequests = i;
                break;
            }
        }

        if (indexNonchangingBalanceTransferRequests == NOT_FOUND) {
            return transferRequests.size();
        }

        for (int i = indexNonchangingBalanceTransferRequests + 1; i < transferRequests.size(); ++i) {
            if (transferRequests[i][0] != transferRequests[i][1]) {
                transferRequests[indexNonchangingBalanceTransferRequests] = transferRequests[i];
                ++indexNonchangingBalanceTransferRequests;
            }
        }
        return indexNonchangingBalanceTransferRequests;
    }
};
