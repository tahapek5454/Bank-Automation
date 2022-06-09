-- phpMyAdmin SQL Dump
-- version 5.3.0-dev+20220608.c947f28e00
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 09 Haz 2022, 18:06:40
-- Sunucu sürümü: 10.4.24-MariaDB
-- PHP Sürümü: 8.0.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `banka`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `durum`
--

CREATE TABLE `durum` (
  `id` int(11) NOT NULL,
  `gelir` double NOT NULL,
  `gider` double NOT NULL,
  `kaar` double NOT NULL,
  `bakiye` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `durum`
--

INSERT INTO `durum` (`id`, `gelir`, `gider`, `kaar`, `bakiye`) VALUES
(1, 1100, 46050, -44950, 955050);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `faiz`
--

CREATE TABLE `faiz` (
  `id` int(11) NOT NULL,
  `faiz` int(11) NOT NULL,
  `gfaiz` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `faiz`
--

INSERT INTO `faiz` (`id`, `faiz`, `gfaiz`) VALUES
(2, 9, 12);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hatalep`
--

CREATE TABLE `hatalep` (
  `id` int(11) NOT NULL,
  `had` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `pbirimi` int(11) NOT NULL DEFAULT 1,
  `mtc` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `iid` int(11) NOT NULL DEFAULT 5
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hesap`
--

CREATE TABLE `hesap` (
  `hno` int(11) NOT NULL,
  `had` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `bakiye` double NOT NULL DEFAULT 0,
  `pbirimi` int(11) NOT NULL DEFAULT 1,
  `mtc` varchar(50) COLLATE utf8_turkish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `hesap`
--

INSERT INTO `hesap` (`hno`, `had`, `bakiye`, `pbirimi`, `mtc`) VALUES
(1, 'hesap1', 385, 1, '38549675120'),
(2, 'hesap2', 505, 1, '39538465891'),
(3, 'hesap3', 463, 2, '39876548214'),
(5, 'hesap5', 551.9375, 3, '38549675120'),
(19, 'banka', 0, 1, NULL);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hstalep`
--

CREATE TABLE `hstalep` (
  `id` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `mtc` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `iid` int(11) NOT NULL DEFAULT 6
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `islem`
--

CREATE TABLE `islem` (
  `ino` int(11) NOT NULL,
  `tutar` int(11) NOT NULL,
  `tarih` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `iid` int(11) NOT NULL,
  `kno` int(11) NOT NULL,
  `hno` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `islem`
--

INSERT INTO `islem` (`ino`, `tutar`, `tarih`, `iid`, `kno`, `hno`) VALUES
(5, 20, '2022-04-12', 1, 3, 2),
(6, 20, '2022-04-12', 1, 3, 1),
(7, 15, '2022-04-12', 1, 1, 3);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `itipi`
--

CREATE TABLE `itipi` (
  `iid` int(11) NOT NULL,
  `iad` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `itipi`
--

INSERT INTO `itipi` (`iid`, `iad`) VALUES
(1, 'para gonderme'),
(2, 'para cekme'),
(3, 'kredi'),
(4, 'borc odeme'),
(5, 'hesap acma'),
(6, 'hesap silme'),
(7, 'para yatirma');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kredi`
--

CREATE TABLE `kredi` (
  `kid` int(11) NOT NULL,
  `miktar` int(11) NOT NULL,
  `dfaiz` int(11) NOT NULL DEFAULT 2,
  `hno` int(11) NOT NULL,
  `aylik` double NOT NULL,
  `afaiz` double NOT NULL,
  `agfaiz` double NOT NULL DEFAULT 0,
  `kalan_nborc` double NOT NULL,
  `odurum` int(11) NOT NULL DEFAULT 2,
  `kalan_gun` int(11) NOT NULL DEFAULT 10,
  `tarih` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `ktalep`
--

CREATE TABLE `ktalep` (
  `id` int(11) NOT NULL,
  `hno` int(11) NOT NULL,
  `miktar` int(11) NOT NULL,
  `mtc` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `iid` int(11) NOT NULL DEFAULT 3
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `maas`
--

CREATE TABLE `maas` (
  `mid` int(11) NOT NULL,
  `maas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `maas`
--

INSERT INTO `maas` (`mid`, `maas`) VALUES
(1, 12000);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `madres`
--

CREATE TABLE `madres` (
  `id` int(11) NOT NULL,
  `adres` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `madres`
--

INSERT INTO `madres` (`id`, `adres`, `tc_no`) VALUES
(1, 'sakarya/seker ', '39538465891'),
(2, 'sakaryaaa/serdivan', '78541265893'),
(3, 'kocaeli/umuttepe', '38549675120');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `meposta`
--

CREATE TABLE `meposta` (
  `id` int(11) NOT NULL,
  `eposta` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `meposta`
--

INSERT INTO `meposta` (`id`, `eposta`, `tc_no`) VALUES
(1, 'fatma@gmail.com', '78541265893'),
(2, 'ayse@gmail.com', '78541265893'),
(3, 'taha@gmail.com', '39876548214');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `mtelefon`
--

CREATE TABLE `mtelefon` (
  `id` int(11) NOT NULL,
  `tel_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `mtelefon`
--

INSERT INTO `mtelefon` (`id`, `tel_no`, `tc_no`) VALUES
(1, '05434024497', '78541265893'),
(2, '05424776588', '48657239444');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `mudur`
--

CREATE TABLE `mudur` (
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `ad` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `soyad` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `sifre` varchar(50) COLLATE utf8_turkish_ci NOT NULL DEFAULT '123'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `mudur`
--

INSERT INTO `mudur` (`tc_no`, `ad`, `soyad`, `sifre`) VALUES
('12345678912', 'Taha', 'Pek', '123');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `musteri`
--

CREATE TABLE `musteri` (
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `ad` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `soyad` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `sifre` varchar(50) COLLATE utf8_turkish_ci NOT NULL DEFAULT '123',
  `t_tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `musteri`
--

INSERT INTO `musteri` (`tc_no`, `ad`, `soyad`, `sifre`, `t_tc_no`) VALUES
('12357845698', 'Busra', 'Pek', '123', '98765432112'),
('12568745698', 'Sibel', 'Tek', '123', '98765432112'),
('25478963214', 'Oguzhan', 'Ozyakup', '123', '32746582456'),
('38549675120', 'Kemalll', 'Acikgoz', '123', '54876932147'),
('39538465891', 'Berk', 'Tek', '123', '98765432112'),
('39876548214', 'Ece', 'Yilmaz', '123', '54876932147'),
('46872314589', 'Selin', 'Korkmaz', '123', '32746582456'),
('48657239444', 'Meltem', 'Kara', '123', '54876932147'),
('75641285478', 'Mehmet', 'Kardes', '123', '98765432112'),
('78541265893', 'Arda', 'Koc', '123', '98765432112'),
('89632145879', 'Arda', 'Kardesler', '123', '32746582456');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `pbirimi`
--

CREATE TABLE `pbirimi` (
  `id` int(11) NOT NULL,
  `bad` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `kur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `pbirimi`
--

INSERT INTO `pbirimi` (`id`, `bad`, `kur`) VALUES
(1, 'TL', 1),
(2, 'dolar', 15),
(3, 'euro', 16),
(4, 'mark', 5);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tadres`
--

CREATE TABLE `tadres` (
  `adres` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `tadres`
--

INSERT INTO `tadres` (`adres`, `tc_no`) VALUES
('istanbul/besiktas', '32746582456'),
('ankara/merkez', '54876932147'),
('sakarya/serdivan cilek', '98765432112');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `talep`
--

CREATE TABLE `talep` (
  `id` int(11) NOT NULL,
  `m_tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `iid` int(11) NOT NULL,
  `onay` int(11) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tarih`
--

CREATE TABLE `tarih` (
  `id` int(11) NOT NULL,
  `tarih` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `tarih`
--

INSERT INTO `tarih` (`id`, `tarih`) VALUES
(1, '2022-04-12');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `temsilci`
--

CREATE TABLE `temsilci` (
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `ad` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `soyad` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `sifre` varchar(50) COLLATE utf8_turkish_ci NOT NULL DEFAULT '123',
  `maasid` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `temsilci`
--

INSERT INTO `temsilci` (`tc_no`, `ad`, `soyad`, `sifre`, `maasid`) VALUES
('32746582456', 'Semih', 'Dizdar', '123', 1),
('54876932147', 'Ayse', 'Sonmez', '123', 1),
('98765432112', 'Emirhan', 'Koç', '123', 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `teposta`
--

CREATE TABLE `teposta` (
  `eposta` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `teposta`
--

INSERT INTO `teposta` (`eposta`, `tc_no`) VALUES
('mrb@gmail.com', '32746582456'),
('mye@gmail.com', '54876932147'),
('ex@gmail.com', '98765432112');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `ttelefon`
--

CREATE TABLE `ttelefon` (
  `tel_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `tc_no` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `ttelefon`
--

INSERT INTO `ttelefon` (`tel_no`, `tc_no`) VALUES
('05434789966', '32746582456'),
('05424776589', '54876932147'),
('05424068877', '98765432112');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `durum`
--
ALTER TABLE `durum`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `faiz`
--
ALTER TABLE `faiz`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `hatalep`
--
ALTER TABLE `hatalep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `x` (`pbirimi`),
  ADD KEY `y` (`mtc`),
  ADD KEY `z` (`iid`);

--
-- Tablo için indeksler `hesap`
--
ALTER TABLE `hesap`
  ADD PRIMARY KEY (`hno`),
  ADD KEY `icerir` (`pbirimi`),
  ADD KEY `acar` (`mtc`);

--
-- Tablo için indeksler `hstalep`
--
ALTER TABLE `hstalep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `q` (`hno`),
  ADD KEY `w` (`mtc`),
  ADD KEY `e` (`iid`);

--
-- Tablo için indeksler `islem`
--
ALTER TABLE `islem`
  ADD PRIMARY KEY (`ino`),
  ADD KEY `icerir2` (`iid`),
  ADD KEY `alır` (`hno`),
  ADD KEY `yapar` (`kno`);

--
-- Tablo için indeksler `itipi`
--
ALTER TABLE `itipi`
  ADD PRIMARY KEY (`iid`);

--
-- Tablo için indeksler `kredi`
--
ALTER TABLE `kredi`
  ADD PRIMARY KEY (`kid`),
  ADD KEY `faizdurumu` (`dfaiz`),
  ADD KEY `ceker` (`hno`);

--
-- Tablo için indeksler `ktalep`
--
ALTER TABLE `ktalep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `o` (`hno`),
  ADD KEY `p` (`iid`),
  ADD KEY `acabilir` (`mtc`);

--
-- Tablo için indeksler `maas`
--
ALTER TABLE `maas`
  ADD PRIMARY KEY (`mid`);

--
-- Tablo için indeksler `madres`
--
ALTER TABLE `madres`
  ADD PRIMARY KEY (`id`),
  ADD KEY `a` (`tc_no`);

--
-- Tablo için indeksler `meposta`
--
ALTER TABLE `meposta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `b` (`tc_no`);

--
-- Tablo için indeksler `mtelefon`
--
ALTER TABLE `mtelefon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `c` (`tc_no`);

--
-- Tablo için indeksler `mudur`
--
ALTER TABLE `mudur`
  ADD PRIMARY KEY (`tc_no`);

--
-- Tablo için indeksler `musteri`
--
ALTER TABLE `musteri`
  ADD PRIMARY KEY (`tc_no`),
  ADD KEY `yonetir` (`t_tc_no`);

--
-- Tablo için indeksler `pbirimi`
--
ALTER TABLE `pbirimi`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `tadres`
--
ALTER TABLE `tadres`
  ADD PRIMARY KEY (`adres`),
  ADD KEY `sahip6` (`tc_no`);

--
-- Tablo için indeksler `talep`
--
ALTER TABLE `talep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `talepeder` (`m_tc_no`),
  ADD KEY `talepler` (`iid`);

--
-- Tablo için indeksler `tarih`
--
ALTER TABLE `tarih`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `temsilci`
--
ALTER TABLE `temsilci`
  ADD PRIMARY KEY (`tc_no`),
  ADD KEY `maaslar` (`maasid`);

--
-- Tablo için indeksler `teposta`
--
ALTER TABLE `teposta`
  ADD PRIMARY KEY (`eposta`),
  ADD KEY `sahip5` (`tc_no`);

--
-- Tablo için indeksler `ttelefon`
--
ALTER TABLE `ttelefon`
  ADD PRIMARY KEY (`tel_no`),
  ADD KEY `sahip4` (`tc_no`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `durum`
--
ALTER TABLE `durum`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Tablo için AUTO_INCREMENT değeri `faiz`
--
ALTER TABLE `faiz`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Tablo için AUTO_INCREMENT değeri `hatalep`
--
ALTER TABLE `hatalep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Tablo için AUTO_INCREMENT değeri `hesap`
--
ALTER TABLE `hesap`
  MODIFY `hno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Tablo için AUTO_INCREMENT değeri `hstalep`
--
ALTER TABLE `hstalep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Tablo için AUTO_INCREMENT değeri `islem`
--
ALTER TABLE `islem`
  MODIFY `ino` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `itipi`
--
ALTER TABLE `itipi`
  MODIFY `iid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `kredi`
--
ALTER TABLE `kredi`
  MODIFY `kid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- Tablo için AUTO_INCREMENT değeri `ktalep`
--
ALTER TABLE `ktalep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Tablo için AUTO_INCREMENT değeri `maas`
--
ALTER TABLE `maas`
  MODIFY `mid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Tablo için AUTO_INCREMENT değeri `madres`
--
ALTER TABLE `madres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Tablo için AUTO_INCREMENT değeri `meposta`
--
ALTER TABLE `meposta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Tablo için AUTO_INCREMENT değeri `mtelefon`
--
ALTER TABLE `mtelefon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Tablo için AUTO_INCREMENT değeri `pbirimi`
--
ALTER TABLE `pbirimi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Tablo için AUTO_INCREMENT değeri `talep`
--
ALTER TABLE `talep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Tablo için AUTO_INCREMENT değeri `tarih`
--
ALTER TABLE `tarih`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `hatalep`
--
ALTER TABLE `hatalep`
  ADD CONSTRAINT `x` FOREIGN KEY (`pbirimi`) REFERENCES `pbirimi` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `y` FOREIGN KEY (`mtc`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `z` FOREIGN KEY (`iid`) REFERENCES `itipi` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `hesap`
--
ALTER TABLE `hesap`
  ADD CONSTRAINT `acar` FOREIGN KEY (`mtc`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `icerir` FOREIGN KEY (`pbirimi`) REFERENCES `pbirimi` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `hstalep`
--
ALTER TABLE `hstalep`
  ADD CONSTRAINT `e` FOREIGN KEY (`iid`) REFERENCES `itipi` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `q` FOREIGN KEY (`hno`) REFERENCES `hesap` (`hno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `w` FOREIGN KEY (`mtc`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `islem`
--
ALTER TABLE `islem`
  ADD CONSTRAINT `alır` FOREIGN KEY (`hno`) REFERENCES `hesap` (`hno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `icerir2` FOREIGN KEY (`iid`) REFERENCES `itipi` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `yapar` FOREIGN KEY (`kno`) REFERENCES `hesap` (`hno`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `kredi`
--
ALTER TABLE `kredi`
  ADD CONSTRAINT `ceker` FOREIGN KEY (`hno`) REFERENCES `hesap` (`hno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `faizdurumu` FOREIGN KEY (`dfaiz`) REFERENCES `faiz` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `ktalep`
--
ALTER TABLE `ktalep`
  ADD CONSTRAINT `acabilir` FOREIGN KEY (`mtc`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `o` FOREIGN KEY (`hno`) REFERENCES `hesap` (`hno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `p` FOREIGN KEY (`iid`) REFERENCES `itipi` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `madres`
--
ALTER TABLE `madres`
  ADD CONSTRAINT `a` FOREIGN KEY (`tc_no`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `meposta`
--
ALTER TABLE `meposta`
  ADD CONSTRAINT `b` FOREIGN KEY (`tc_no`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `mtelefon`
--
ALTER TABLE `mtelefon`
  ADD CONSTRAINT `c` FOREIGN KEY (`tc_no`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `musteri`
--
ALTER TABLE `musteri`
  ADD CONSTRAINT `yonetir` FOREIGN KEY (`t_tc_no`) REFERENCES `temsilci` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `tadres`
--
ALTER TABLE `tadres`
  ADD CONSTRAINT `sahip6` FOREIGN KEY (`tc_no`) REFERENCES `temsilci` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `talep`
--
ALTER TABLE `talep`
  ADD CONSTRAINT `talepeder` FOREIGN KEY (`m_tc_no`) REFERENCES `musteri` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `talepler` FOREIGN KEY (`iid`) REFERENCES `itipi` (`iid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `temsilci`
--
ALTER TABLE `temsilci`
  ADD CONSTRAINT `maaslar` FOREIGN KEY (`maasid`) REFERENCES `maas` (`mid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `teposta`
--
ALTER TABLE `teposta`
  ADD CONSTRAINT `sahip5` FOREIGN KEY (`tc_no`) REFERENCES `temsilci` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Tablo kısıtlamaları `ttelefon`
--
ALTER TABLE `ttelefon`
  ADD CONSTRAINT `sahip4` FOREIGN KEY (`tc_no`) REFERENCES `temsilci` (`tc_no`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;



