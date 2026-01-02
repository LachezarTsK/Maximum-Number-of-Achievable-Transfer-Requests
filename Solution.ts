
function maximumRequests(numberOfBuildings: number, transferRequests: number[][]): number {
    const numberOfBalanceChangingTransferRequests = moveBalanceChangingTransferRequestsToTheFront(transferRequests);
    const numberOfNonbalanceChangingTransferRequests = transferRequests.length - numberOfBalanceChangingTransferRequests;

    let maxNumberOfAchievableTransferRequests = 0;
    let upperLimit = Math.pow(2, numberOfBalanceChangingTransferRequests);

    for (let i = 1; i < upperLimit; ++i) {

        let value = i;
        let index = 0;
        let numberOfRequests = 0;
        const balance = new Array(numberOfBuildings).fill(0);

        while (value > 0) {
            if ((value & 1) === 1) {
                const from = transferRequests[index][0];
                const to = transferRequests[index][1];
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
};

class Util {
    static NOT_FOUND = -1;
    static BALANCED = 0;
}

function allBuildingsAreBalanced(balance: number[]): boolean {
    for (let current of balance) {
        if (current !== Util.BALANCED) {
            return false;
        }
    }
    return true;
}

function moveBalanceChangingTransferRequestsToTheFront(transferRequests: number[][]): number {
    let indexNonchangingBalanceTransferRequests = Util.NOT_FOUND;
    for (let i = 0; i < transferRequests.length; ++i) {
        if (transferRequests[i][0] === transferRequests[i][1]) {
            indexNonchangingBalanceTransferRequests = i;
            break;
        }
    }

    if (indexNonchangingBalanceTransferRequests === Util.NOT_FOUND) {
        return transferRequests.length;
    }

    for (let i = indexNonchangingBalanceTransferRequests + 1; i < transferRequests.length; ++i) {
        if (transferRequests[i][0] !== transferRequests[i][1]) {
            transferRequests[indexNonchangingBalanceTransferRequests] = transferRequests[i];
            ++indexNonchangingBalanceTransferRequests;
        }
    }
    return indexNonchangingBalanceTransferRequests;
}
