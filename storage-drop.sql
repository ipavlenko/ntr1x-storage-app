ALTER TABLE images DROP FOREIGN KEY FK_images_ResourceId
ALTER TABLE resources_images DROP FOREIGN KEY FK_resources_images_ResourceId
ALTER TABLE resources_images DROP FOREIGN KEY FK_resources_images_RelateId
ALTER TABLE resources_images DROP FOREIGN KEY FK_resources_images_ImageId
ALTER TABLE grants DROP FOREIGN KEY FK_grants_ResourceId
ALTER TABLE grants DROP FOREIGN KEY FK_grants_UserId
ALTER TABLE sessions DROP FOREIGN KEY FK_sessions_UserId
ALTER TABLE sessions DROP FOREIGN KEY FK_sessions_ResourceId
ALTER TABLE users_tokens DROP FOREIGN KEY FK_users_tokens_ResourceId
ALTER TABLE users_tokens DROP FOREIGN KEY FK_users_tokens_UserId
ALTER TABLE users DROP FOREIGN KEY FK_users_ResourceId
ALTER TABLE domains DROP FOREIGN KEY FK_domains_ResourceId
ALTER TABLE domains DROP FOREIGN KEY FK_domains_PortalId
ALTER TABLE portals DROP FOREIGN KEY FK_portals_UserId
ALTER TABLE portals DROP FOREIGN KEY FK_portals_ResourceId
ALTER TABLE portals DROP FOREIGN KEY FK_portals_ThumbnailId
ALTER TABLE aspects DROP FOREIGN KEY FK_aspects_RelateId
DROP TABLE resources
DROP TABLE images
DROP TABLE resources_images
DROP INDEX INDEX_grants_Pattern_Action ON grants
DROP TABLE grants
DROP TABLE sessions
DROP TABLE users_tokens
DROP INDEX INDEX_users_Scope_Origin_Identity_Email ON users
DROP TABLE users
DROP TABLE domains
DROP TABLE portals
DROP TABLE aspects
