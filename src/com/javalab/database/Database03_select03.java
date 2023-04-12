package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 카테고리가 "전자제품"인 상품들의 정보를 출력하세요
 *  - 출력할 컬럼 : 상품ID, 상품명, 가격, 입고일, 케테고리ID, 카테고리명
 *  - 가격순 정렬 **/
public class Database03_select03 {

	public static void main(String[] args) {
		// 오라클 드라이버 로딩 문자
				String driver = "oracle.jdbc.driver.OracleDriver";
				
				// 데이터베이스 연결 문자열
				String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
				
				// 데이터베이스 계정명
				String dbId = "tempdb";
				
				// 데이터베이스 비밀번호
				String dbPwd = "1234";
				
				Connection con = null;			// 데이터베이스 연결 객체
				PreparedStatement ps = null;	// 커넥션 객체를 통해서 데이터베이스에 쿼리를 실행해주는 객체
				ResultSet rs = null;			// 실행된 쿼리문의 결과를 반환 받는 객체
				
				String sql;
				
				try {
					Class.forName(driver);
					System.out.println("드라이버 로드 성공");
					
					con = DriverManager.getConnection(url,dbId,dbPwd);
					System.out.println("커넥션 객체 생성 완료");
					System.out.println();
					
					String str = "전자제품";
					
					sql = "select p.product_id \"상품ID\", p.product_name \"상품명\", p.price \"가격\", p.receipt_date \"입고일\", c.category_id \"카테고리ID\", c.category_name \"카테고리명\"";
					sql += " from category c, product p";
					sql += " where c.category_id = p.category_id";
					sql += " and c.category_name = ?";
					sql += " order by p.price desc";
					
					ps = con.prepareStatement(sql);
					ps.setString(1, str);
					rs = ps.executeQuery();
					
					while (rs.next()) {
						System.out.println(rs.getInt("상품ID") + "\t" +
											rs.getString("상품명") + "\t" +
											rs.getInt("가격") + "\t" +
											rs.getDate("입고일") + "\t" +
											rs.getInt("카테고리ID") + "\t" +
											rs.getString("카테고리명"));
						}
					}catch (ClassNotFoundException e) {
						System.out.println("드라이버 ERR: " + e.getMessage());
					}catch (SQLException e) {
						System.out.println("SQL ERR: " + e.getMessage());
					}finally {
						try {
							if (rs != null) {
								rs.close();
							}
							if (ps != null) {
								ps.close();
							}
							if (con != null) {
								con.close();
							}
						}catch (SQLException e) {
							System.out.println("자원해제 ERR: " + e.getMessage());
						}
					}
	} // main e

} // class e
