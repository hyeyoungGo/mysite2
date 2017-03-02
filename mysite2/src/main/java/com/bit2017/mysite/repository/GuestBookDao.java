package com.bit2017.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.bit2017.mysite.vo.GuestBookVo;

import oracle.jdbc.pool.OracleDataSource;

@Repository
public class GuestBookDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private OracleDataSource dateSource;
	
	public List<GuestBookVo> getList() {
		return sqlSession.selectList("guestbook.getList");
	}
	
	public List<GuestBookVo> getList(int page) {
		/*StopWatch stopWatch = new StopWatch();
		
		stopWatch.start();
		
		List<GuestBookVo> list = sqlSession.selectList("guestbook.getListByPage", page);
		
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeMillis());*/
		
		return sqlSession.selectList("guestbook.getListByPage", page);
	}
	
	public boolean insert(GuestBookVo guestBookVo) {
		int count = sqlSession.insert("guestbook.insert", guestBookVo);
		//insert 후에 PK 받아내기
		System.out.println(guestBookVo);
		return (count == 1);
	}
	
	public boolean delete(GuestBookVo guestBookVo) {
		int count = sqlSession.delete("guestbook.delete", guestBookVo);
		return (count == 1);
	}
	
	public GuestBookVo get(Long no) {
		System.out.println(no);
		GuestBookVo v = sqlSession.selectOne("guestbook.getByNo", no);
		System.out.println(v);
		return v;
	}

	
	public boolean modify(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = dateSource.getConnection();
			System.out.println("dao" + vo);
			//3. SQL문 준비
				sql = "update guestbook set name = ?, content = ?  where no = ? and password = ?";
				pstmt = conn.prepareStatement(sql);
				//4. binding
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getNo());
				pstmt.setString(4, vo.getPassword());
				System.out.println("pstmt" + vo);
			//5. SQL문 실행
			int count = pstmt.executeUpdate();
			System.out.println(count);
			//6.결과
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error: " + e);
			//finally는 항상 꼭 실행된다.
		} finally {
			//3. 자원정리
			try {
				if( pstmt != null) {
					pstmt.close();
				}
				if( conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		}
		return result;
	}

	
}
