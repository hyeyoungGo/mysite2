package com.bit2017.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2017.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 :" + e);
		}
		return conn;
	}
	
	public List<BoardVo> getList(Long pageNo) {
		return sqlSession.selectList("board.getList");
	}
	
	public boolean updateHit( Long boardNo ) {
		int count = sqlSession.update("board.hit", boardNo);
		return (count == 1);
	}
	
	public BoardVo get(Long boardNo) {
		return sqlSession.selectOne("board.getByNo", boardNo);
	}
	
	public boolean modify(BoardVo boardVo) {
		int count = sqlSession.update("board.modify", boardVo);
		return (count == 1);
	}
	
	public int write(BoardVo boardVo) {
		return sqlSession.insert("board.write", boardVo);
	}
	
	public void insert( BoardVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			if( vo.getGroupNo() == null ) {
				/* 새글 등록 */
				String sql = 
					" insert" +
					"   into board" +
					" values( seq_board.nextval, ?, ?, sysdate, 0, nvl((select max(g_no) from board),0) + 1, 1, 0, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString( 1, vo.getTitle() );
				pstmt.setString( 2, vo.getContent() );
				pstmt.setLong( 3, vo.getUserNo() );
			} else {
				/* 답글 등록 */
				String sql = 
					" insert" +
					"   into board" +
					" values( seq_board.nextval, ?, ?, sysdate, 0, ?, ?, ?, ? )"; 
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString( 1, vo.getTitle() );
				pstmt.setString( 2, vo.getContent() );
				pstmt.setInt( 3, vo.getGroupNo() );
				pstmt.setInt( 4, vo.getOrderNo() );
				pstmt.setInt( 5, vo.getDepth() );
				pstmt.setLong( 6, vo.getUserNo() );
			}

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
	}
	
	public int getTotalCount( String keyword ) {
		int totalCount = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			if( "".equals( keyword ) ) {
				String sql = "select count(*) from board";
				pstmt = conn.prepareStatement(sql);
			} else { 
				String sql =
					"select count(*)" +
					"  from board" +
					" where title like ? or content like ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
			}
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				totalCount = rs.getInt( 1 );
			}
		} catch (SQLException e) {
			System.out.println( "error:" + e );
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch ( SQLException e ) {
				System.out.println( "error:" + e );
			}  
		}
		
		return totalCount;
	}
	
	public int increaseGroupOrder( Integer groupNo, Integer orderNo ) {
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		System.out.println("daogroup:"+map);
		return sqlSession.update("board.group", map);
	}
	
	public boolean delete(BoardVo boardVo) {
		int count = sqlSession.delete("board.delete", boardVo);
		return (count == 1);
	}
	

}
