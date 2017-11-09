package com.relation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.follow_tool_man.model.Follow_tmVO;

public class RelationJDBCDAO implements RelationDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BA104_G3";
	String passwd = "BA104"; 
	
	
	private static final String INSERT_STMT=
			"INSERT INTO relation (mem_no,related_mem_no,relation_status)"
			+ "VALUES (?,?,?)";
	
	private static final String GET_ALL_STMT=
			"SELECT related_mem_no,relation_status"
			+ " FROM relation WHERE mem_no=? order by mem_no";
	
	private static final String SELECT=
			"SELECT mem_no,related_mem_no,relation_status"
			+ " FROM relation WHERE mem_no=?";
	
	private static final String UPDATE=
			"UPDATE relation SET relation_status=?"
			+ "WHERE mem_no=? and related_mem_no=?";
	
	private static final String DELETE=
			"DELETE FROM relation WHERE mem_no=? and related_mem_no=? ";
	
	
	
	

	@Override
	public void insert(RelationVO relationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, relationVO.getMem_no());
			pstmt.setString(2, relationVO.getRelated_mem_no());
			pstmt.setInt(3, relationVO.getRelation_status());
			
			pstmt.executeUpdate();
		
		}catch (ClassNotFoundException e) {
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
	public void update(RelationVO relationVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, relationVO.getRelation_status());
			pstmt.setString(2, relationVO.getMem_no());
			pstmt.setString(3, relationVO.getRelated_mem_no());
			
			
			pstmt.executeUpdate();
		
		}catch (ClassNotFoundException e) {
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
	public void delete(String mem_no, String related_mem_no) {

		RelationVO relationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, mem_no);
			pstmt.setString(2, related_mem_no);
			
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} finally{	
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
	}

	@Override
	public RelationVO findByPrimaryKey(String mem_no, String related_mem_no) {
		RelationVO relationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT);
			
			pstmt.setString(1, mem_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				relationVO = new RelationVO();
				relationVO.setMem_no(rs.getString("mem_no"));
				relationVO.setRelated_mem_no(rs.getString("related_mem_no"));
				relationVO.setRelation_status(rs.getInt("relation_status"));
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
		return relationVO;
	}

	@Override
	public List<RelationVO> getAllRelationWithMem_no(String mem_no) {
		List<RelationVO> list = new ArrayList<RelationVO>();
		RelationVO relationVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			pstmt.setString(1, mem_no);
			rs =pstmt.executeQuery();
			
			while(rs.next()){
				relationVO = new RelationVO();
				relationVO.setRelated_mem_no(rs.getString("related_mem_no"));
				relationVO.setRelation_status(rs.getInt("relation_status"));
				list.add(relationVO);
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
			
		
		return list;
	}

	public static void main(String[] args){
		
		RelationJDBCDAO dao = new RelationJDBCDAO();
		
		//�s�W
		RelationVO relationVO = new RelationVO();
		
		relationVO.setMem_no("M000001");
		relationVO.setRelated_mem_no("M000005");
		relationVO.setRelation_status(1);
		
		dao.insert(relationVO);
		
		//�ק�
		RelationVO relationVO2 = new RelationVO();
		
		relationVO2.setMem_no("M000001");
		relationVO2.setRelated_mem_no("M000005");
		relationVO2.setRelation_status(2);
		
		dao.update(relationVO2);
		
		//�R��
//		dao.delete("M000015", "M000016");
		
		//�d�߳�@
		RelationVO relationVO3 = dao.findByPrimaryKey("M000001", "M000005");
		System.out.println("relation_status :" + relationVO3.getRelation_status());
		System.out.println("================================================");
		
		//�d�ߩҦ�XXX�|�������������Y���H
		
		List<RelationVO> list = dao.getAllRelationWithMem_no("M000001");
		for(RelationVO aRelation : list){
			System.out.println("��ӷ|�������Y���|�����U :");
			System.out.println("�|�����X :" + aRelation.getRelated_mem_no());
			System.out.println("�����Y�� :" + aRelation.getRelation_status());
		}
		
	}
	
}
