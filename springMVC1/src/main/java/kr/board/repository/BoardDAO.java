package kr.board.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.board.entity.Board;

@Repository
public class BoardDAO {
	
	@Autowired
	DataSource dataSource;
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void finallyClose() {
		try {
			if (pstmt != null) { pstmt.close();}
			if (conn != null) { conn.close(); }
			if (rs != null) { rs.close(); }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public ArrayList<Board> getLists(){
    	ArrayList<Board> list = new ArrayList<>();
    	
    	try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT * FROM myboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				
				board.setIdx(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setContent(rs.getNString(3));
				board.setWriter(rs.getString(4));
				board.setIndate(rs.getString(5));
				board.setCount(rs.getInt(6));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			finallyClose();
		}
    	return list;
    }
    
    public Board boardContent(int idx) {
    	Board board = new Board();
    	
    	try {
    		conn = dataSource.getConnection();
    		
    		String sql = "SELECT * FROM myboard WHERE idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board.setIdx(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setContent(rs.getNString(3));
				board.setWriter(rs.getString(4));
				board.setIndate(rs.getString(5));
				board.setCount(rs.getInt(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			finallyClose();
		}
    	System.out.println(board);
    	return board;
    }
    
    public int updateBoard(Board board) {
    	try {
    		conn = dataSource.getConnection();
    		
    		String sql = "UPDATE myboard SET title=?, content=? WHERE idx=?";
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, board.getTitle());
    		pstmt.setString(2, board.getContent());
    		pstmt.setInt(3, board.getIdx());
    		
    		return pstmt.executeUpdate();
    		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			finallyClose();
		}
    	return 0;
    }

}
