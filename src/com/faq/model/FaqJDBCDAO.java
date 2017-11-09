package com.faq.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaqJDBCDAO implements FaqDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BA104_G3";
	String passwd = "BA104"; 
	
	private static final String INSERT_STMT=
			"INSERT INTO faq (faq_no,faq_content,faq_date) VALUES"
			+ " ('F'||LPAD(FAQ_NO.NEXTVAL,4,'0'),?,?)";
	
	private static final String GET_ALL_STMT=
			"SELECT faq_no,faq_content,faq_date FROM faq order by faq_no";
	
	private static final String SELECT=
			"SELECT faq_no,faq_content,faq_date FROM faq WHERE faq_no=?";
	
	private static final String UPDATE=
			"UPDATE faq SET faq_content=? ,faq_date=? WHERE faq_no=?";
	
	private static final String DELETE=
			"DELETE FROM faq WHERE faq_no = ?";
	
	@Override
	public void insert(FaqVO faqVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
//			pstmt.setString(1, faqVO.getFaq_no());
			pstmt.setString(1, faqVO.getFaq_content());
			pstmt.setDate(2, faqVO.getFaq_date());
			
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
			if (con != null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(FaqVO faqVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, faqVO.getFaq_content());
			pstmt.setDate(2, faqVO.getFaq_date());
			pstmt.setString(3, faqVO.getFaq_no());
			
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
			if (con != null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String faq_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, faq_no);
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
			if (con != null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public FaqVO findByPrimaryKey(String faq_no) {
		
		FaqVO faqVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT);
			
			pstmt.setString(1, faq_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				faqVO = new FaqVO();
				faqVO.setFaq_no(rs.getString("faq_no"));
				faqVO.setFaq_content(rs.getString("faq_content"));
				faqVO.setFaq_date(rs.getDate("faq_date"));
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{	
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}				
		return faqVO;
	}

	@Override
	public List<FaqVO> getAll() {
		List<FaqVO> list = new ArrayList<FaqVO>();
		FaqVO faqVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				faqVO = new FaqVO();
				faqVO.setFaq_no(rs.getString("faq_no"));
				faqVO.setFaq_content(rs.getString("faq_content"));
				faqVO.setFaq_date(rs.getDate("faq_date"));
				list.add(faqVO);
			}
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{	
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}				
		return list;
	}
	
	public static void main(String[] args){
		FaqJDBCDAO dao = new FaqJDBCDAO();
		
		//�s�W
		FaqVO faqVO1 = new FaqVO();
		
		faqVO1.setFaq_content("��5�B�ڷQ�ǥ����U�O�H�W�[�ۤv�������A�p�󪾹D�ۤv�٦����ǯ�O�ݭn�[�j�H ��5�B�i�H�Ҽ{�쥻�����Ʀ�]�A�j�M�ݬݥثe���@������O���ݨD�ʤ����");
		faqVO1.setFaq_date(Date.valueOf("2017-11-01"));
		
		dao.insert(faqVO1);
		
		//�ק�
		FaqVO faqVO2 = new FaqVO();
		
		faqVO2.setFaq_content("��5�B�Ʀ�]�W����ƻP�ƾڦh�[��s�@���H ��6�B�ڭ̦��g�Ʀ�B��Ʀ�B�u�Ʀ�A�H��K�W�[�n���ץH�Φ��B�~���ʼ��y�n���A�n���i�H��ӵ{�I���ӫ~");
		faqVO2.setFaq_date(Date.valueOf("2017-11-02"));
		faqVO2.setFaq_no("F0005");
		dao.update(faqVO2);
		
		//�R��
//		dao.delete("F0005");
		
		//�d�߳�@
		
		FaqVO faqVO3 = dao.findByPrimaryKey("F0001");
		System.out.println(faqVO3.getFaq_no() + ",");
		System.out.println(faqVO3.getFaq_content() + ",");
		System.out.println(faqVO3.getFaq_date());
		System.out.println("===============================================");
		
		//�d�ߥ���
		List<FaqVO> list = dao.getAll();
		for(FaqVO aFaq : list){
			System.out.println(aFaq.getFaq_no() + ",");
			System.out.println(aFaq.getFaq_content() + ",");
			System.out.println(aFaq.getFaq_date());
			System.out.println("===============================================");
		}
		
		
	}

}
