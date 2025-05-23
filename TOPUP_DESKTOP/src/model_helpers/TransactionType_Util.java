package model_helpers;

import java.util.List;
import model_db.StatusInfo;
import model_db.TransactionType;
import model_util.HibernateUtil;
import model_util.hqlQueriesHelper;
import org.hibernate.Session;

/**
 * StatusInfo generated by hbm2java
 */
public class TransactionType_Util {

    public List getAllTransactionType(Session session, String suffix) {
        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM TransactionType where flag=0", suffix);

    }
  public List getAllTransactionType( String suffix) {
              Session session = HibernateUtil.getSessionFactory().openSession();

        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM TransactionType where flag=0", suffix);

    }
    public TransactionType getTransactionType_by_id(Session session, int id, String suffix) {
        List list = hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM TransactionType where flag=0 and idtransactionType = " + id, suffix);
        if (list.isEmpty()) {
            return null;
        } else {
            return ((TransactionType) list.get(0));
        }
    }

    public List getTransacType_by_transactionTypeDesc_Like(Session session, String statusInfoDesc, String suffix) {
        return hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM TransactionType where flag=0 and transactionTypeDesc LIKE '%" + statusInfoDesc + "%'", suffix);
    }

    public TransactionType getTransacType_by_transactionTypeDesc(Session session, String statusInfoDesc, String suffix) {
        List list = hqlQueriesHelper.ExecuteSelectHqlQuery_WithPreparedSession(session, "FROM TransactionType where flag=0 and transactionTypeDesc = '" + statusInfoDesc + "'", suffix);
        if (list.isEmpty()) {
            return null;
        } else {
            return ((TransactionType) list.get(0));
        }
    }

    public void addOfferInfo(TransactionType adt, Session session) {
        hqlQueriesHelper.executeAddHQLQuery_WithPreparedSession(adt, session);
    }

    public void updateOfferInfo(TransactionType adt, Session session) {
        hqlQueriesHelper.executeUpdateHQLQuery_WithPreparedSession(adt, session);
    }

}
