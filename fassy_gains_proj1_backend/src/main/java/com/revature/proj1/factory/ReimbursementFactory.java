package com.revature.proj1.factory;

import java.sql.Connection;
import java.util.HashMap;

public class ReimbursementFactory
{
    public void ReimbursementFactory()
    {

    }

    public void handleReimbursement(HashMap<String, String> myReimb)
        {
            String monies = myReimb.get("money");
            float f = Float.parseFloat(monies);
            try (Connection connection = ConnectionFactory.getConnection())
            {
                String sql = "Insert into ers_reimbursements()";
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
}
