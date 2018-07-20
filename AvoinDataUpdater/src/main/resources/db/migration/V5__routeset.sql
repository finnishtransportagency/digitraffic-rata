create table `routeset` (
  `id` bigint(20) not null,
  `message_time` datetime not null,
  `route_type` varchar(10) default null,
  `train_number` varchar(64) not null,
  `departure_date` date default null,
  `client_system` varchar(16) default null,
  `version` bigint(20) not null,
  `virtual_departure_date` date generated always as (if(isnull(`departure_date`),cast(convert_tz(`message_time`,'utc','europe/helsinki') as date),`departure_date`)) virtual not null,
  primary key (`id`),
  key `virtualDepartureDate_trainnumber` (`virtual_departure_date`,`train_number`),
  key `version` (`version`)
);

create table `routesection` (
  `id` bigint(20) not null,
  `section_id` varchar(64) default null,
  `station_code` varchar(10) default null,
  `routeset_id` bigint(20) not null,
  `commercial_track_id` varchar(10) default null,
  `section_order` bigint(20) default null,
  primary key (`id`),
  key `routesection_fk_idx` (`routeset_id`),
  key `station_idx` (`station_code`,`commercial_track_id`),
  constraint `routesection_fk` foreign key (`routeset_id`) references `routeset` (`id`) on delete cascade
);