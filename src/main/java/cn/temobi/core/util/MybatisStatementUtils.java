package cn.temobi.core.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisStatementUtils implements Serializable {

	private static final long serialVersionUID = -4971789510892178325L;

	protected Logger log = LoggerFactory.getLogger(getClass());

	public static String getDeleteStatement() {
		return "delete";
	}

	public static String getInsertStatement() {
		return "insert";
	}

	public static String getUpdateStatement() {
		return "update";
	}

	public static String getByIdStatement() {
		return "getById";
	}

	public static String getCountStatement() {
		return "count";
	}

	public static String getFindAllStatement() {
		return "findAll";
	}

	public static String getFindByMapStatement() {
		return "findByMap";
	}

	public static String getFindByPageStatement() {
		return "findByPage";
	}

}
