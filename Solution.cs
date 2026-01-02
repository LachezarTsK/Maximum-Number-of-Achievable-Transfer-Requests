
using System;

public class Solution
{
    private static readonly int NOT_FOUND = -1;
    private static readonly int BALANCED = 0;

    public int MaximumRequests(int numberOfBuildings, int[][] transferRequests)
    {
        int numberOfBalanceChangingTransferRequests = MoveBalanceChangingTransferRequestsToTheFront(transferRequests);
        int numberOfNonbalanceChangingTransferRequests = transferRequests.Length - numberOfBalanceChangingTransferRequests;

        int maxNumberOfAchievableTransferRequests = 0;
        int upperLimit = (int)Math.Pow(2, numberOfBalanceChangingTransferRequests);

        for (int i = 1; i < upperLimit; ++i)
        {

            int value = i;
            int index = 0;
            int numberOfRequests = 0;
            int[] balance = new int[numberOfBuildings];

            while (value > 0)
            {
                if ((value & 1) == 1)
                {
                    int from = transferRequests[index][0];
                    int to = transferRequests[index][1];
                    --balance[from];
                    ++balance[to];
                    ++numberOfRequests;
                }
                value >>= 1;
                ++index;
            }

            if (maxNumberOfAchievableTransferRequests < numberOfRequests && AllBuildingsAreBalanced(balance))
            {
                maxNumberOfAchievableTransferRequests = numberOfRequests;
            }
        }

        maxNumberOfAchievableTransferRequests += numberOfNonbalanceChangingTransferRequests;
        return maxNumberOfAchievableTransferRequests;
    }

    private bool AllBuildingsAreBalanced(int[] balance)
    {
        foreach (int current in balance)
        {
            if (current != BALANCED)
            {
                return false;
            }
        }
        return true;
    }

    private int MoveBalanceChangingTransferRequestsToTheFront(int[][] transferRequests)
    {
        int indexNonchangingBalanceTransferRequests = NOT_FOUND;
        for (int i = 0; i < transferRequests.Length; ++i)
        {
            if (transferRequests[i][0] == transferRequests[i][1])
            {
                indexNonchangingBalanceTransferRequests = i;
                break;
            }
        }

        if (indexNonchangingBalanceTransferRequests == NOT_FOUND)
        {
            return transferRequests.Length;
        }

        for (int i = indexNonchangingBalanceTransferRequests + 1; i < transferRequests.Length; ++i)
        {
            if (transferRequests[i][0] != transferRequests[i][1])
            {
                transferRequests[indexNonchangingBalanceTransferRequests] = transferRequests[i];
                ++indexNonchangingBalanceTransferRequests;
            }
        }
        return indexNonchangingBalanceTransferRequests;
    }
}
