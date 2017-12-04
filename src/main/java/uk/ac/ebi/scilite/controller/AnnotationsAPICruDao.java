package uk.ac.ebi.scilite.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.ac.ebi.literature.mongodb.dao.impl.CrudDaoImpl;

public class AnnotationsAPICruDao extends CrudDaoImpl {

	private final static Logger logger = LogManager.getLogger(AnnotationsAPICruDao.class);
	
	public AnnotationsAPICruDao(MONGODB_URL dbUrl) throws IOException {
		super(dbUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void logError(Exception e) {
		logger.error("MongoDB ERROR:"+e.getMessage(), e);
	}
	
}
