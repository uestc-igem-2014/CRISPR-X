CREATE VIEW view_getsgrna AS 
SELECT SName, Chr_Name, c.Chr_No, r.sgrna_ID, sgrna_start, sgrna_end, sgrna_strand, sgrna_seq, sgrna_PAM, pam_PAM, result_ntlength, result_Sspe, result_Seff, result_offtarget
FROM Table_sgRNA as r 
LEFT JOIN Table_result as t ON r.sgrna_ID=t.sgrna_ID
JOIN Table_chromosome as c ON c.Chr_No=r.Chr_No
JOIN Table_Specie as s ON s.Sno=c.Sno
JOIN Table_PAM as p ON p.pam_id=r.pam_id
;

CREATE VIEW view_allsgrna AS 
SELECT SName, Chr_Name, c.Chr_No, r.sgrna_ID, sgrna_start, sgrna_end, sgrna_strand, sgrna_seq, sgrna_PAM, pam_PAM
FROM Table_sgRNA as r
JOIN Table_chromosome as c ON c.Chr_No=r.Chr_No
JOIN Table_Specie as s ON s.Sno=c.Sno
JOIN Table_PAM as p ON p.pam_id=r.pam_id
;
