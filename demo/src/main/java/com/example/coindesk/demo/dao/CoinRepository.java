package com.example.coindesk.demo.dao;

import com.example.coindesk.demo.bean.Coin;
import com.example.coindesk.demo.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long>{

    //默认提供了Optional<User> findById(Long id);

    public Coin findByPcode(String pcode);
    // public Coin findByPcodeS(String pcode);
    public List<Coin> findAll();
    // @Query("select u from Coin u where u.id <= ?1")
    // Page<Coin> findMore(Long maxId, Pageable pageable);

    // @Modifying
    // @Transactional
    // @Query("update Coin u set u.name = ?1 where u.id = ?2")
    // int updateById(String name, Long id);


}
